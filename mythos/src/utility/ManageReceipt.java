package utility;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entity.*;

/**
 * Classe che si occupa di gestire la creazione del documento da stampare.
 */
@WebServlet("/TestPrint")
public class ManageReceipt {

	private String filename;

	/**
	 * Metodo che crea il documento.
	 * @param cart {@link Cart} cart!=null carrello.
	 * @param order {@link Order} order!=null ordine.
	 * @param filename {@link String} filename!=null nome file.
	 */
	public void createDocument(Cart cart, Order order, String filename) throws DocumentException, IOException {
		// setto variabile d'istanza per la stampa
		this.filename = filename;
		float total = 0;
		int qtaTot = 0;
		//unità di misura:points
		float w = 2.025f * 72;
		float h = 8.26772f * 72;
		
		Rectangle pagesize = new Rectangle(w, h);
		Document document = new Document(pagesize);
		document.setMargins(0, 0, -5, 0);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font f1 = new Font(bf, 10);
		Font f2 = new Font(bf, 11, Font.BOLD);
		// Font f3=new Font(bf, 10, Font.ITALIC);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		document.add(new Paragraph("*************************************", f1));
		// sarà sicuramente non nullo poichè l'user non può confermare un ordine vuoto
		document.add(new Paragraph("ORDINE AL TAVOLO N° " + order.getTable().getTableNumber() + "-"
				+ order.getTable().getReservationName(), new Font(bf, 12)));
		document.add(new Paragraph("\n"));
		// document.add( Chunk.NEWLINE );

		PdfPTable table = new PdfPTable(4); // 4 columns.

		table.setWidthPercentage(100);

		float[] columnWidths = { 2f, 0.5f, 0.7f, 0.2f };

		table.setWidths(columnWidths);

		PdfPCell cell1 = new PdfPCell(new Paragraph("Prodotto", f2));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Qta.", f2));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Totale", f2));
		cell3.setColspan(2);
		cell1.setPaddingBottom(8);

		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		cell2.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		cell3.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);

		table.completeRow();

		// per ogni prodotto presente nel carrello
		for (CartProduct prod : cart.getProductList().values()) {

			// genero la cella della prodotto in base alle note
			String productString = "";
			productString = prod.getProduct().getName() + "-" + prod.getProduct().getDescription();
			cell1 = new PdfPCell(new Paragraph(productString, f1));

			cell2 = new PdfPCell(new Paragraph("" + prod.getQuantity(), f1));
			cell3 = new PdfPCell(new Paragraph("" + (prod.getProduct().getPrice() * prod.getQuantity()), f1));
			PdfPCell cell4 = new PdfPCell(new Paragraph("€", f1));

			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell4.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);

			cell1.setPaddingBottom(8);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);

			table.completeRow();
			total += (prod.getProduct().getPrice() * prod.getQuantity());
			qtaTot += prod.getQuantity();
		}

		// aggiungo i totali
		cell1 = new PdfPCell(new Paragraph("Totale", new Font(bf, 10, Font.BOLD)));
		cell2 = new PdfPCell(new Paragraph("" + qtaTot, new Font(bf, 10, Font.BOLD)));
		cell3 = new PdfPCell(new Paragraph("" + total, new Font(bf, 10, Font.BOLD)));
		PdfPCell cell4 = new PdfPCell(new Paragraph("€", new Font(bf, 10, Font.BOLD)));
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cell3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell4.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		cell2.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		cell3.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		cell4.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.completeRow();

		document.add(table);

		// creo la lista per visualizzare i dettagli dell ordine
		// ****************************************************

		PdfPTable tableDetails = new PdfPTable(1); // 4 columns.

		tableDetails.setWidthPercentage(100);

		PdfPCell cellDetail = new PdfPCell(new Paragraph("Dettagli:", f2));
		cellDetail.setPaddingBottom(8);

		cellDetail.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		cellDetail.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		tableDetails.addCell(cellDetail);

		tableDetails.completeRow();

		Chunk bullet = new Chunk("\u2022", new Font());

		List list = new List(List.UNORDERED);
		list.setIndentationLeft(8);
		list.setAutoindent(true);
		list.setListSymbol(bullet);

		for (Detail d : cart.getDetailsList().values())
			list.add(new ListItem("\t" + d.getDescription() + "\n"));

		// se c'è almeno un dettaglio nel carrello
		if (cart.getDetailsList().size() > 0) {
			document.add(new Paragraph("\n"));
			document.add(tableDetails);
			document.add(list);
			document.add(Chunk.NEWLINE);
		}

		document.add(
				new Paragraph("#Persone al tavolo: " + order.getTable().getPeopleNumber(), new Font(bf, 8, Font.BOLD)));
		document.add(new Paragraph("Data Ordine: " + order.getDateOrder(), new Font(bf, 8, Font.ITALIC)));
		document.add(new Paragraph("*************************************", f1));
		// far visualizzare i dettagli del tavolo se sono disponibili
		document.close();
		writer.close();
	}

	/**
	 * Metodo per inviare la stampa.
	 * @return {@link Boolean} esito stampa.
	 */
	public boolean printToPrinter() throws ServletException, IOException {

		if (filename.length() <= 0)
			return false;

		PDDocument document = null;
		try {
			document = PDDocument.load(new File(filename));
			// document.save(new File("C:\\Users\\Windows\\Desktop\\test.pdf"));
			PrintService serv = PrintToPrinter.findPrintService("server");

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(document));

			try {
				job.setPrintService(serv);
				job.print();
				document.close();

			} catch (PrinterException e) {
				e.printStackTrace();
				return false;
			}
		} finally {
			if (document != null)
				document.close();
		}
		return true;
	}

	/**
	 * Metodo che crea il documento per i dettagli in un ordine.
	 * @param cart {@link Cart} cart!=null carrello.
	 * @param filename {@link String} filename!=null nome file.
	 * @param tableNumber {@link Integer} tableNumber!=null && tableNumber>0 numero tavolo.
	 */
	public void createDetailsDocument(Cart cart, String filename, int tableNumber)
			throws DocumentException, IOException {

		this.filename = filename;

		float w = 2.025f * 72;
		float h = 8.26772f * 72;
		Rectangle pagesize = new Rectangle(w, h);
		Document document = new Document(pagesize);
		document.setMargins(0, 0, -5, 0);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font f1 = new Font(bf, 12);
		Font f2 = new Font(bf, 11, Font.BOLD);
		// Font f3=new Font(bf, 10, Font.ITALIC);

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		document.add(new Paragraph("******************************", f1));

		// sarà sicuramente non nullo poichè l'user non può confermare un ordine vuoto
		document.add(new Paragraph("DETTAGLIO TAVOLO N° - " + tableNumber, f1));
		document.add(new Paragraph("\n"));

		PdfPTable table = new PdfPTable(1); // 4 columns.

		table.setWidthPercentage(100);

		PdfPCell cell1 = new PdfPCell(new Paragraph("Dettagli:", f2));
		cell1.setPaddingBottom(8);

		cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		table.addCell(cell1);

		table.completeRow();

		document.add(table);

		Chunk bullet = new Chunk("\u2022", new Font());

		List list = new List(List.UNORDERED);
		list.setIndentationLeft(8);
		list.setAutoindent(true);
		list.setListSymbol(bullet);

		for (Detail td : cart.getDetailsList().values())
			list.add(new ListItem("\t" + td.getDescription() + "\n"));

		document.add(list);

		document.add(new Paragraph("******************************", f1));

		// far visualizzare i dettagli del tavolo se sono disponibili
		try {
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		writer.close();
	}
}
