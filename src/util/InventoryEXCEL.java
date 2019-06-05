package util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.Phone;
public class InventoryEXCEL {
	private List<Phone> inventoryDate;

    public InventoryEXCEL(List<Phone> date)
    {
        this.inventoryDate = date;
    }

	public void createExcel(String path) throws WriteException, IOException {
		OutputStream os = new FileOutputStream(path);
		// 创建工作薄
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		// 创建新的一页
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
		Label phoneName = new Label(0, 0, "名称");
		sheet.addCell(phoneName);
		Label phoneNumber = new Label(1, 0, "型号");
		sheet.addCell(phoneNumber);
		Label bandType = new Label(2, 0, "手机品牌");
		sheet.addCell(bandType);
		Label manufacturers = new Label(3, 0, "生产厂家");
		sheet.addCell(manufacturers);
		Label produceTime = new Label(4, 0, "生产时间");
		sheet.addCell(produceTime);
		Label inventory = new Label(5, 0, "库存数量");
		sheet.addCell(inventory);
		Label price = new Label(6, 0, "定价");
		sheet.addCell(price);
		Label total = new Label(7, 0, "总码样");
		sheet.addCell(total);
		int count_RKTotal = 0;
		float count_RKMoney = 0;
		for (int i = 1; i <= inventoryDate.size(); i++)
			for (int j = 0; j < 8; j++) {
				Label date = new Label(j, i, String.valueOf(inventoryDate.get(i - 1).get(j)));
				sheet.addCell(date);
				if( j == 5)
					count_RKTotal += Integer.valueOf(inventoryDate.get(i - 1).get(j).toString());
				else if(j == 7)
					count_RKMoney += Integer.valueOf(inventoryDate.get(i - 1).get(5).toString())*inventoryDate.get(i - 1).getPrice();
			}
		int index = inventoryDate.size() + 1;
		Label totalAll = new Label(0, index, "总计");
		sheet.addCell(totalAll);
		Label totalNumber = new Label(5, index, String.valueOf(count_RKTotal));
		sheet.addCell(totalNumber);
		Label totalMoney = new Label(7, index, new DecimalFormat("###,###,##0.00").format(count_RKMoney));
		sheet.addCell(totalMoney);
		// 把创建的内容写入到输出流中，并关闭输出流
		workbook.write();
		workbook.close();
		os.close();
	}
}
