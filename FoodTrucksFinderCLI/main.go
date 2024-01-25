package main

import (
	"crypto/tls"
	"fmt"
	"github.com/go-resty/resty/v2"
	"github.com/jszwec/csvutil"
	"github.com/spf13/cobra"
	"os"
	"strings"
	"time"
)

func main() {
	var name string // no  usage
	var rootCmd = &cobra.Command{
		Use:   "CLIFoodTrucksFinder [Applicant or LocationDescription character] ",
		Short: "please input query string to filter with food trucks name . Get and filter USA San Francisco Goverment food truck data",
		Long:  `Downloads and filters CSV data from SF Gov API`,
		Args:  cobra.ExactArgs(1),
		Run: func(cmd *cobra.Command, args []string) {

			inputString := args[0]
			fmt.Printf("Input string: %s\n", inputString)

			name = inputString
			client := resty.New()
			csvPath := "usa_sf_food_trucks_data.csv"
			// Check if CSV file exists
			if _, err := os.Stat(csvPath); os.IsNotExist(err) {
				fmt.Println("Not Found USA CSV, Downloading CSV...")
				err := downloadCSV(client, "https://data.sfgov.org/api/views/rqzj-sfat/rows.csv", csvPath)
				if err != nil {
					fmt.Println("Error downloading CSV:", err)
					return
				}
			} else {
				fmt.Println("Using existing CSV file.")
			}
			// Read and filter CSV
			data, err := readCSVThanFilter(csvPath, name)
			if err != nil {
				fmt.Println("Error reading CSV:", err)
				return
			}
			// Print the filtered data
			fmt.Println("Filtered Data:")
			printData(data)

			//demoCode(csvPath)
		},
	}
	//rootCmd.Flags().StringVarP(&name, "name", "n", "", "Name to filter applicants")
	//rootCmd.MarkFlagRequired("name") // Name flag is required

	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

func demoCode(filePath string) {

	bytes, _ := os.ReadFile(filePath)

	var foodTrucks []FoodTruck
	if err := csvutil.Unmarshal(bytes, &foodTrucks); err != nil {
		fmt.Println("error:", err)
	}

	for _, foodTruck := range foodTrucks {
		fmt.Println(fmt.Sprintf(" { lat: %s, lng: %s },", foodTruck.Latitude, foodTruck.Longitude))
	}

}
func GetHttpClient() *resty.Client {
	client := resty.New()
	client.SetTLSClientConfig(&tls.Config{InsecureSkipVerify: true})
	client.SetTimeout(10 * time.Second)
	//client.SetHeaders(GetCommonHeaders())
	client.SetContentLength(true)
	//client.SetProxy("socks5://127.0.0.1:7890")
	client.
		SetRetryCount(5).
		SetRetryWaitTime(10 * time.Second).
		SetDebug(false)
	return client
}
func downloadCSV(client *resty.Client, url, filePath string) error {
	resp, err := GetHttpClient().R().
		//EnableTrace().
		Get(url)

	if err != nil {
		return err
	}
	return os.WriteFile(filePath, resp.Body(), 0644)
}

// readCSVThanFilter reads the CSV file from the given path and filters by name
func readCSVThanFilter(filePath, nameFilter string) ([]FoodTruck, error) {
	//file, err := os.Open(filePath)
	//if err != nil {
	//	return nil, err
	//}
	//defer file.Close()

	//reader := csv.NewReader(file)
	//rows, err := reader.ReadAll()
	//if err != nil {
	//	return nil, err
	//}

	bytes, err := os.ReadFile(filePath)
	if err != nil {
		return nil, err
	}

	var foodTrucks []FoodTruck
	if err := csvutil.Unmarshal(bytes, &foodTrucks); err != nil {
		fmt.Println("error:", err)
	}

	//for _, u := range foodTrucks {
	//	fmt.Printf("%+v\n", u)
	//}
	var filteredData []FoodTruck = make([]FoodTruck, 0)
	for _, row := range foodTrucks[1:] { // Skip header row
		if strings.Contains(strings.ToLower(row.Applicant), strings.ToLower(nameFilter)) ||
			strings.Contains(strings.ToLower(row.LocationDescription), strings.ToLower(nameFilter)) {
			filteredData = append(filteredData, row)
		}
	}
	return filteredData, nil
}

// printData prints the filtered data in a table format
func printData(data []FoodTruck) {
	// Implement table printing logic
	// For simplicity, printing in a basic format
	for _, d := range data {
		fmt.Printf("[locationid] %s,[Applicant] is %s,[LocationDescription] is %s\n", d.LocationID, d.Applicant, d.LocationDescription)
	}
}

type FoodTruck struct {
	LocationID              string `json:"locationid" csv:"locationid"`                               // 位置ID
	Applicant               string `json:"applicant" csv:"Applicant"`                                 // 申请人
	FacilityType            string `json:"facility_type" csv:"Facility Type"`                         // 设施类型
	CNN                     string `json:"cnn" csv:"CNN"`                                             // CNN（可能是某种代码或标识）
	LocationDescription     string `json:"location_description" csv:"LocationDescription"`            // 位置描述
	Address                 string `json:"address" csv:"Address"`                                     // 地址
	BlockLot                string `json:"block_lot" csv:"BlockLot"`                                  // 街区地块
	Block                   string `json:"block" csv:"Block"`                                         // 街区
	Lot                     string `json:"lot" csv:"Lot"`                                             // 地块
	Permit                  string `json:"permit" csv:"Permit"`                                       // 许可证
	Status                  string `json:"status" csv:"Status"`                                       // 状态
	FoodItems               string `json:"food_items" csv:"Food Items"`                               // 食品项目
	X                       string `json:"x" csv:"X"`                                                 // X坐标
	Y                       string `json:"y" csv:"Y"`                                                 // Y坐标
	Latitude                string `json:"latitude" csv:"Latitude"`                                   // 纬度
	Longitude               string `json:"longitude" csv:"Longitude"`                                 // 经度
	Schedule                string `json:"schedule" csv:"Schedule"`                                   // 时间表
	DaysHours               string `json:"days_hours" csv:"Dayshours"`                                // 天小时（营业时间）
	NOISent                 string `json:"noi_sent" csv:"NOISent"`                                    // NOI发送
	Approved                string `json:"approved" csv:"Approved"`                                   // 已批准
	Received                string `json:"received" csv:"Received"`                                   // 已接收
	PriorPermit             string `json:"prior_permit" csv:"Prior Permit"`                           // 先前许可证
	ExpirationDate          string `json:"expiration_date" csv:"ExpirationDate"`                      // 过期日期
	Location                string `json:"location" csv:"Location"`                                   // 位置
	FirePreventionDistricts string `json:"fire_prevention_districts" csv:"Fire Prevention Districts"` // 消防预防区域
	PoliceDistricts         string `json:"police_districts" csv:"Police Districts"`                   // 警察区域
	SupervisorDistricts     string `json:"supervisor_districts" csv:"Supervisor Districts"`           // 监督区域
	ZipCodes                string `json:"zip_codes" csv:"Zip Codes"`                                 // 邮政编码
	NeighborhoodsOld        string `json:"neighborhoods_old" csv:"Neighborhoods (old)"`               // 老邻里
}
