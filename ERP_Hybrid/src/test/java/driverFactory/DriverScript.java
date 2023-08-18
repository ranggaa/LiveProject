package driverFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript extends FunctionLibrary {
String inputpath ="./FileInput/Controller.xlsx";
String outputpath ="./FileOutPut/HybridResults.xlsx";
String TestCases="MasterTestCases";
String TCModule="";
ExtentReports report;
ExtentTest  test;
public void startTest()throws Throwable
{
	String Module_Status="";
	//create object for excel file util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate al test cases in TestCases sheet
	for(int i=1;i<=xl.rowCount(TestCases);i++)
	{
		String Exe_Status = xl.getCellData(TestCases, i, 2);
		if(Exe_Status.equalsIgnoreCase("Y"))
		{
			//store corresponding sheet into TCModule
			TCModule =xl.getCellData(TestCases, i, 1);
			//define path of ExtentReport
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+"  "+".html");
			test=report.startTest(TCModule);
			//iterate all for all testcases
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule
				String Description =xl.getCellData(TCModule, j, 0);
				String Function_Name = xl.getCellData(TCModule, j, 1);
				String Locator_Type =xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try
				{
					if(Function_Name.equalsIgnoreCase("startBrowser"))
					{
						driver =FunctionLibrary.startBrowser();
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("captureData"))
					{
						FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Name.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable(driver);
						test.log(LogStatus.INFO, Description);
					}
					//writer as pass into status cell
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					test.log(LogStatus.PASS, Description);
					Module_Status="True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//writer as Fail into status cell
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					test.log(LogStatus.FAIL, Description);
					Module_Status="False";
				}
				
			}
			if(Module_Status.equalsIgnoreCase("True"))
			{
				xl.setCellData(TestCases, i, 3, "Pass", outputpath);
			}
			if(Module_Status.equalsIgnoreCase("False"))
			{
				xl.setCellData(TestCases, i, 3, "Fail", outputpath);
			}
			report.endTest(test);
			report.flush();
			
		}
		else
		{
			//write as blocked into Testcases sheet  for text case flag to N
			xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
		}
	}
}
}














