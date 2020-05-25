package utility;

import java.awt.print.PrinterJob;

import javax.print.*;

/**
 * Classe che si occupa di gestire la stampa.
 */
public class  PrintToPrinter {

	/**
	 * Restituisce il servizio di stampa specificato (null se non lo trova).
	 * @param printerName {@link String} printerName!=null indica il nome della stampante.
	 * @return {@link PrintService} 
	 */
	public static PrintService findPrintService(String printerName) {

	    PrintService service = null;

	    // Get array of all print services - sort order NOT GUARANTEED!
	    PrintService[] services = PrinterJob.lookupPrintServices();

	    // Retrieve specified print service from the array
	    for (int index = 0; service == null && index < services.length; index++) {

	        if (services[index].getName().equalsIgnoreCase(printerName)) {

	            service = services[index];
	        }
	    }

	    // Return the print service
	    return service;
	}

	/**
	 * Restituisce un'istanza di printer job con il printer service specificato.
	 * @param printerName {@link String} printerName!=null indica il nome della stampante.
	 * @return {@link PrinterJob}
	 */
	public static PrinterJob findPrinterJob(String printerName) throws Exception {

	    // Retrieve the Printer Service
	    PrintService printService = findPrintService(printerName);

	    // Validate the Printer Service
	    if (printService == null) {

	        throw new IllegalStateException("Unrecognized Printer Service \"" + printerName + '"');
	    }

	    // Obtain a Printer Job instance.
	    PrinterJob printerJob = PrinterJob.getPrinterJob();

	    // Set the Print Service.
	    printerJob.setPrintService(printService);

	    // Return Print Job
	    return printerJob;
	}

	/**
	 * L'elenco delle stampanti non si aggiorna necessariamente se si modifica l'elenco di
	 * stampanti all'interno dell'O / S; eseguire per aggiornare se necessario.
	 */
	@SuppressWarnings("rawtypes")
	public static void refreshSystemPrinterList() {

		Class[] classes = PrintServiceLookup.class.getDeclaredClasses();

	    for (int i = 0; i < classes.length; i++) {

	        if ("javax.print.PrintServiceLookup$Services".equals(classes[i].getName())) {

	            sun.awt.AppContext.getAppContext().remove(classes[i]);
	            break;
			}
		}
	}
}
