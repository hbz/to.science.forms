package services;

import de.hbz.lobid.helper.Etikett;

public class ZettelFields {
	public static Etikett creatorZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/creator");
	public static Etikett titleZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/title");
	public static Etikett dataOriginZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#dataOrigin");
	public static Etikett abstractTextZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/abstract");
	public static Etikett embargoZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#embargoTime");
	public static Etikett languageZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/language");
	public static Etikett licenseZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#license");
	public static Etikett professionalGroupZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#professionalGroup");
	public static Etikett subjectZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/subject");
	public static Etikett yearOfCopyrightZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#yearOfCopyright");
	public static Etikett mediumZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/medium");
	public static Etikett ddcZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#ddc");
	public static Etikett doiZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/ontology/bibo/doi");
}
