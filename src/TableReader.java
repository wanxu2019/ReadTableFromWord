import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;  
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.usermodel.Paragraph;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.hwpf.usermodel.Table;  
import org.apache.poi.hwpf.usermodel.TableCell;  
import org.apache.poi.hwpf.usermodel.TableIterator;  
import org.apache.poi.hwpf.usermodel.TableRow; 

public class TableReader extends BaseTools {

	//从word文档中读取表格数据存入list中并返回
	public  ArrayList<TableData> read(String path) throws IOException {
		FileInputStream in = new FileInputStream(new File(path));  
        HWPFDocument hwpf = new HWPFDocument(in);  
        Range range = hwpf.getRange();// 得到文档的读取范围  
        TableIterator it = new TableIterator(range);  
        ArrayList<TableData> list=new ArrayList<TableData>();
        int count=0;
        // 迭代文档中的表格  
        while (it.hasNext()) {  
            Table tb = (Table) it.next();  
            TableData data=new TableData();
            // 迭代行，默认从0开始  
            for (int i = 0; i < tb.numRows(); i++) {  
                TableRow tr = tb.getRow(i);  
                // 迭代列，默认从0开始  
                for (int j = 0; j < tr.numCells(); j++) {  
                    TableCell td = tr.getCell(j);// 取得单元格  
                    // 取得单元格的内容  
                    StringBuffer sb=new StringBuffer();
                    for (int k = 0; k < td.numParagraphs(); k++) {  
                        Paragraph para = td.getParagraph(k);  
                        sb.append(para.text());  
                    } 
                    String string=sb.toString().trim();
                    if(i==0&&j==1)
                    {
                    	data.setClazz(string);
                    }
                    else if(i==1&&j==1)
                    {
                    	data.setName(string);
                    }
                    else if(i==2&&j==1)
                    {
                    	data.setLifeCircle(string);
                    }
                    else if(i==2&&j==3)
                    {
                    	data.setTheme(string);
                    }
                    else if(i==3&&j==3)
                    {
                    	data.setForm(string);
                    }
                    else if(i==4&&j==1)
                    {
                    	data.setKeywords(string);
                    }
                    else if(i==9&&j==1)
                    {
                    	data.setContent(string);
                    }
                }  
            }
//            show("第"+count+"条："+data.toString());
            count+=1;
            list.add(data);
        }
		return list;
	}
}

