package ila.restclient.creditmantri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;

public class IlaCalls {
	
	
	 
	

	public static void main(String[] args) throws JsonProcessingException {
		
		Boolean notAtEnd= true;
		
		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(new File(args[0]));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(excelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();

		while (iterator.hasNext() && notAtEnd) {

            Row currentRow = iterator.next();
            
            
            Iterator<Cell> cellIterator = currentRow.iterator();
           
            DataFormatter formatter = new DataFormatter();
            String process = formatter.formatCellValue(cellIterator.next());
            
            if (process.equals("End Processing"))
            	{	
            	notAtEnd = false;
            	break;
            	}
            else if(process.equals("*"))
            {
             String user = formatter.formatCellValue(cellIterator.next());
             String pass  = formatter.formatCellValue(cellIterator.next());
             String type = formatter.formatCellValue(cellIterator.next());
             String Action = formatter.formatCellValue(cellIterator.next());
             
             if(user.equals("UserName"))
             {continue;}
             IlaApi a = new IlaApi(user,pass);
             switch (a.statuscode)
             {
             case 401: System.out.println("Invalid username or password: Skipping record--- |User ID : "+user+"|Offer Type:"+ type+"|Action:"+ Action);
             break;
             case 200:
             	switch (Action)
             		{
             			case "All Offers":
             			a.AllOffers(type);
             			break;
             	case "Prefilled Details": 
             			a.GetPrefilled(type);
             			break;
             	case "Save Answers":
             			List<Answer> answerarray = new ArrayList<Answer>();
             			int updateCount = 0;
             			while (cellIterator.hasNext()) 
             				{
             					//if(!formatter.formatCellValue(cellIterator.next()).isEmpty())
             					{
             					Answer answer = new Answer();
            		      		try {
            		      			answer.setAnswer(formatter.formatCellValue(cellIterator.next()),formatter.formatCellValue(cellIterator.next()) );
            		      			updateCount++;
            		      		} catch (NoSuchElementException e) {}
            		      		if (updateCount > 0) {
            		      			answerarray.add(answer); 
            		      		}}
             				}
             			if (updateCount > 0) {
             			a.UpdateAnswersList(type, answerarray);
             			}
             			break;
             	case "Save Default-Salaried": 
             		Sheet defaultValues = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows = defaultValues.iterator();
             		while (defaultValuesRows.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                        //System.out.println(DefaultType);
                        //System.out.println(type);
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-Sal")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		break;
             	case "Save Default-Self Employed": 
             		Sheet defaultValues1 = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows1 = defaultValues1.iterator();
             		while (defaultValuesRows1.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows1.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                        //System.out.println(DefaultType);
                        //System.out.println(type);
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-Self")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		break;
             	case "Save Default-Self Employed Professional": 
             		Sheet defaultValues11 = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows11 = defaultValues11.iterator();
             		while (defaultValuesRows11.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows11.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                        //System.out.println(DefaultType);
                        //System.out.println(type);
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-SelfProf")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		break;  
             		}
             }
            }
		}
		System.out.println("");
		System.out.println("*********COMPLETED********");
	}
}

             
          

		
		
			
		
		
		
	

	

