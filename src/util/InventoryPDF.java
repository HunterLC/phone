package util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import model.Phone;
public class InventoryPDF {
	private static List<Phone> inventoryDate;

	public InventoryPDF(List<Phone> date) {
		this.inventoryDate = date;
	}

	public static void createPDF(String path) throws DocumentException, IOException {
		
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		// ������������
		BaseFont bfSong = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
		Font font = new Font(bfSong, 20, Font.NORMAL);
		Font fontBold = new Font(bfSong, 11, Font.BOLD);
		Font font1 = new Font(bfSong, 10, Font.NORMAL);
		writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
		document.setPageSize(PageSize.A4);// ����A4
		SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��");
		writer.setPageEvent(new PdfPageEventHelper() {
			public void onEndPage(PdfWriter writer, Document document) {
				PdfContentByte cb = writer.getDirectContent();
				cb.saveState();
				cb.beginText();
				BaseFont bf = null;
				try {
					bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cb.setFontAndSize(bf, 10);
				// Footer
				float y = document.bottom(-20);
				cb.showTextAligned(PdfContentByte.ALIGN_CENTER, String.valueOf(writer.getPageNumber()),
						(document.right() + document.left()) / 2, y, 0);
				cb.endText();

				cb.restoreState();
			}
		});
		document.open();
		PdfContentByte canvas = writer.getDirectContent();
		Phrase phrase1 = new Phrase("�ֻ����һ����", font);
		Phrase phrase2 = new Phrase("�����������ڣ�" + df.format(new Date()), font1);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 230, 780, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 420, 735, 0);

		float[] widths = { 3, 3, 4, 6, 8, 4, 3, 4 }; // ���ø��б���Ŀ���
		PdfPTable table = new PdfPTable(widths);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setLockedWidth(true);
		table.setTotalWidth(523);

		PdfPCell pdfCell = new PdfPCell();
		pdfCell.setMinimumHeight(30);// ���ñ����и�
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		Paragraph paragraph = new Paragraph("����", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("�ͺ�", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("�ֻ�Ʒ��", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("��������", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("����ʱ��", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("�������", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("����", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		paragraph = new Paragraph("������", fontBold);
		pdfCell.setPhrase(paragraph);
		table.addCell(pdfCell);
		
		float inventory = 0, totalMoney = 0;
		for (int i = 0; i < inventoryDate.size(); i++) {
			for (int j = 0; j < 8; j++) {
				PdfPCell pdfCell1 = new PdfPCell();
				pdfCell1.setMinimumHeight(30);// ���ñ����и�
				if (j == 0 || j == 4) {
					pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				if (j > 4) {
					pdfCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				Paragraph paragraph1 = new Paragraph(String.valueOf(inventoryDate.get(i).get(j)), font1);
				pdfCell1.setPhrase(paragraph1);
				table.addCell(pdfCell1);
			}
			inventory += inventoryDate.get(i).getInventory();//�����ܿ��
			totalMoney += inventoryDate.get(i).getInventory()*inventoryDate.get(i).getPrice();//���������ܼ�
		}

		PdfPCell pdfCell1 = new PdfPCell();
		pdfCell1.setMinimumHeight(20);// ���ñ����и�
		pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		Paragraph paragraph1 = new Paragraph("�ϼ�", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(" ", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(" ", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(" ", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(" ", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		pdfCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		paragraph1 = new Paragraph(new DecimalFormat("###,###,###.##").format(inventory), font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(" ", font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);
		paragraph1 = new Paragraph(new DecimalFormat("###,###,##0.00").format(totalMoney), font1);
		pdfCell1.setPhrase(paragraph1);
		table.addCell(pdfCell1);

		document.add(new Paragraph("\n\n\n\n\n"));
		document.add(table);
		document.add(new Paragraph("\n"));
		document.add(new Paragraph(
				"�Ʊ��ˣ�ǩ�֣���                                                                                                  �����(ǩ��):",
				font1));
		document.close();
		writer.close();
	}
}