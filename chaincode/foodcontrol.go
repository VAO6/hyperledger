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