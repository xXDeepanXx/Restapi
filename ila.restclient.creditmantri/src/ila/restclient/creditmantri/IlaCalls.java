package ila.restclient.creditmantri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;

public class IlaCalls {
	
	
	 
	

	public static void main(String[] args) throws JsonProcessingException  {
		
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
        String prevuser = null;
        IlaApi a = null;

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
             
             MyLogger.log(Level.INFO,"User :" + user + "  |Offer Type : "+type+"  |Action : "+   Action);
             if (!user.equals(prevuser)||user.equals(null))
             {a = new IlaApi(user,pass);}
             switch (a.statuscode)
             { 
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
             					{
             					Answer answer = new Answer();
             					String question = formatter.formatCellValue(cellIterator.next());
             					String ans = formatter.formatCellValue(cellIterator.next());
             					if (question != null|| !question.isEmpty())
            		      		{try {
            		      			answer.setAnswer(question,ans);
            		      			updateCount++;
            		      		} catch (NoSuchElementException e) {}
            		      		if (updateCount > 0) {
            		      			answerarray.add(answer); 
            		      		}}}
             				}
             			if (updateCount > 0) {
             			a.UpdateAnswersList(type, answerarray);
             			a.MoveStageAll(type);
             			}
             			a.AllOffers(type);
             			break;
             	case "Compare":
         			List<Answer> answerarrayA = new ArrayList<Answer>();
         			List<Answer> answerarrayB = new ArrayList<Answer>();
         			int updateCount1 = 0;
         			//int updateCountB = 0;
         			String AnswerA = null;
         			String AnswerB = null;
         			while (cellIterator.hasNext()) 
         				{String question,ans = null;
         				try {
         				 question = formatter.formatCellValue(cellIterator.next());
     					 ans = formatter.formatCellValue(cellIterator.next());
         				}catch (NoSuchElementException e) {break;}
     					if (question != null|| !question.isEmpty()) {
         					try {
         					 String compareAnswer[] = ans.split("&&");
         					 AnswerA = compareAnswer[0];
         					AnswerB = compareAnswer[1];
         					}catch  (NoSuchElementException e) {}
         					{
         					Answer answer1 = new Answer();
         					Answer answer2 = new Answer();
        		      		try {
        		      			answer1.setAnswer(question,AnswerA );
        		      			answer2.setAnswer(question,AnswerB );
        		      			updateCount1++;
        		      		} catch (NoSuchElementException e) {}
        		      		if (updateCount1 > 0) {
        		      			answerarrayA.add(answer1); 
        		      			answerarrayB.add(answer2); 
        		      		}
        		      		
         					
         					}}}
         				//System.out.println("Test");
         			a.compare(type,answerarrayA,answerarrayB);
         				
         			/*if (updateCountA > 0) {
         			a.UpdateAnswersList(type, answerarrayA);
         			a.MoveStageAll(type);
         			}
         			a.AllOffers(type);
         			
         			if (updateCountB > 0) {
             			a.UpdateAnswersList(type, answerarrayB);
             			a.MoveStageAll(type);
             			}
             			a.AllOffers(type);*/
         			break;
         	
             	case "Save Default-Salaried": 
             		Sheet defaultValues = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows = defaultValues.iterator();
             		while (defaultValuesRows.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-Sal")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		a.AllOffers(type);
             		break;
             	case "Save Default-Self Employed": 
             		Sheet defaultValues1 = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows1 = defaultValues1.iterator();
             		while (defaultValuesRows1.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows1.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-Self")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		a.AllOffers(type);
             		break;
             	case "Save Default-Self Employed Professional": 
             		Sheet defaultValues11 = workbook.getSheetAt(4);
             		Iterator<Row> defaultValuesRows11 = defaultValues11.iterator();
             		while (defaultValuesRows11.hasNext() && notAtEnd) { 

                        Row defaultcurrentRow = defaultValuesRows11.next();
                        
                        
                        Iterator<Cell> defaultcellIterator = defaultcurrentRow.iterator();
                        String DefaultType = formatter.formatCellValue(defaultcellIterator.next());
                        
                       if(DefaultType.equals(type)||DefaultType.equals(type.concat("-SelfProf")))
                       {
                    	   String RawData  = formatter.formatCellValue(defaultcellIterator.next());
                    	   a.DefaultData(type, RawData);
                    	}
                        
             		}
             		a.MoveStageAll(type);
             		a.AllOffers(type);
             		break;  
             		}
             }
             prevuser = user;
            }
		}
		System.out.println("");
		System.out.println("*********COMPLETED********");
	}
}

             
          

		
		
			
		
		
		
	

	

