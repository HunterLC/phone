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
		// ����������
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		// �����µ�һҳ
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		// ����Ҫ��ʾ������,����һ����Ԫ�񣬵�һ������Ϊ�����꣬�ڶ�������Ϊ�����꣬����������Ϊ����
		Label phoneName = new Label(0, 0, "����");
		sheet.addCell(phoneName);
		Label phoneNumber = new Label(1, 0, "�ͺ�");
		sheet.addCell(phoneNumber);
		Label bandType = new Label(2, 0, "�ֻ�Ʒ��");
		sheet.addCell(bandType);
		Label manufacturers = new Label(3, 0, "��������");
		sheet.addCell(manufacturers);
		Label produceTime = new Label(4, 0, "����ʱ��");
		sheet.addCell(produceTime);
		Label inventory = new Label(5, 0, "�������");
		sheet.addCell(inventory);
		Label price = new Label(6, 0, "����");
		sheet.addCell(price);
		Label total = new Label(7, 0, "������");
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
		Label totalAll = new Label(0, index, "�ܼ�");
		sheet.addCell(totalAll);
		Label totalNumber = new Label(5, index, String.valueOf(count_RKTotal));
		sheet.addCell(totalNumber);
		Label totalMoney = new Label(7, index, new DecimalFormat("###,###,##0.00").format(count_RKMoney));
		sheet.addCell(totalMoney);
		// �Ѵ���������д�뵽������У����ر������
		workbook.write();
		workbook.close();
		os.close();
	}
}