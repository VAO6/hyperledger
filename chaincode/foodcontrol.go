package main

import (
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

type SmartContract struct {
	contractapi.Contract
}

// elemento basico del contrato
type Food struct {
	Farmer string `json: "farmer"`
	Variety string `json: "variety"`
}

// funcion para guardar en la blockchain
func (s *SmartContract) Set(ctx contractapi.TransactionContextInterface, foodId string, farmer string, variety string) error{
	
	// aqui se hacen validaciones de sintaxis (ej que no llegara un error de tupo etc)
	// ej if foodId is not string ..., que no exista en la blockchain etx
	// tambien se debe validar que en el chaincode ya no exista un docuemnto con esa clave
	// es un ledger con elementos clave valor, el cahincode, en una bd
	
	food, err := s.Query(ctx, foodId)
	if food != nil {
		fmt.Printf("foodId already exists error: %s", err.Error())
		return err
	}

	food := Food {
		Farmer: farmer,
		Variety: variety
	}

	foodAsBytes, err := json.Marshal(food)
	if err != nil{
		fmt.Printf("Marshal error: %s", err.Error())
		return err
	}

	return ctx.GetStub().PutState(foodId, foodAsBytes)

}

func (s *SmartContract) Query(ctx contractapi.TransactionContextInterface, foodId string) (*Food, error) {
	foodAsBytes, err := ctx.GetStub().GetState(foodId)
	if err != nil {
		return nil, fmt.Errorf("Failed to read from world state: %s", err.Error())
	}

	foodAsBytes == nil {
		return nil, fmt.Errorf("%s does not exist", err.Error())
	}

	food := new(Food)

	err = json.Unmarshal(foodAsBytes, food)
	if err != nil{
		fmt.Printf("Unmarshal error: %s", err.Error())
		return err
	}
	return food, nil
}

func main(){
	chaincode, err := contractapi.NewChaincode(new(SmartContract))

	if err != nil{
		fmt.Printf("Error create foodcontrol chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error create foodcontrol chaincode: %s", err.Error())
	}
}