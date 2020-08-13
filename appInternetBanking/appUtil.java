package appInternetBanking;

import java.text.DecimalFormat;

public class appUtil {

	public static String formataMoeda(double valor) {
		
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format(valor);
		
		return formatted;
	}

}
