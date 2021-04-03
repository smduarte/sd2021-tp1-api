package tp1.api.service.soap;

import jakarta.xml.ws.WebFault;

@WebFault
public class SheetsException extends Exception {

	private static final long serialVersionUID = 1L;

	public SheetsException() {
		super("");
	}

	public SheetsException(String errorMessage ) {
		super(errorMessage);
	}
}
