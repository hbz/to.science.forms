/*Copyright (c) 2016 "hbz"

This file is part of zettel.

etikett is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package services;

import de.hbz.lobid.helper.Etikett;

/**
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
public class ZettelFields {
	public static Etikett creatorZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/creator");
	public static Etikett contributorZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/contributor");
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
	public static Etikett urnZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/lobid/lv#urn");
	public static Etikett isLikeZF = ZettelHelper.etikett
			.getEtikett("http://www.umbel.org/specifications/vocabulary#isLike");
	public static Etikett fundingZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#funding");
	public static Etikett recordingPeriodZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#recordingPeriod");
	public static Etikett recordingLocationZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#recordingLocation");
	public static Etikett recordingCoordinatesZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#recordingCoordinates");
	public static Etikett previousVersionZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#previousVersion");
	public static Etikett nextVersionZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#nextVersion");
	public static Etikett contributorOrderZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/lobid/lv#contributorOrder");
	public static Etikett subjectOrderZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/lobid/lv#subjectOrder");
	public static Etikett alternativeTitleZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/alternative");
	public static Etikett titleLanguageZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#titleLanguage");
	public static Etikett descriptionZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/dc/terms/description");
	public static Etikett projectIdZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#projectId");
	public static Etikett fundingProgramZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#fundingProgram");
	public static Etikett associatedPublicationZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#associatedPublication");
	public static Etikett associatedDatasetZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#associatedDataset");
	public static Etikett referenceZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#reference");
	public static Etikett creatorNameZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/dc/elements/1.1/creator");
	public static Etikett subjectNameZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/dc/elements/1.1/subject");
	public static Etikett usageManualZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#usageManual");
	public static Etikett contributorNameZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/dc/elements/1.1/contributor");
	public static Etikett reviewStatusZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#reviewStatus");
	public static Etikett congressTitleZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#congressTitle");
	public static Etikett congressLocationZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#congressLocation");
	public static Etikett congressDurationZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#congressDuration");
	public static Etikett isbnZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/ontology/bibo/isbn10");
	public static Etikett publisherZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/dc/elements/1.1/publisher");
	public static Etikett publicationPlaceZF = ZettelHelper.etikett
			.getEtikett("http://rdvocab.info/Elements/placeOfPublication");

}
