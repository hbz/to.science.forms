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
	public static Etikett embargoTimeZF =
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
	public static Etikett fundingIdZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#fundingId");
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
	public static Etikett congressShortTitleZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#congressShortTitle");
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
	public static Etikett containedInZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/lobid/lv#containedIn");
	public static Etikett bibliographicCitationZF = ZettelHelper.etikett
			.getEtikett("http://purl.org/dc/terms/bibliographicCitation");
	public static Etikett volumeInZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#volumeIn");
	public static Etikett issueZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#issue");
	public static Etikett pagesZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#pages");
	public static Etikett articleNumberZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#articleNumber");
	public static Etikett publicationStatusZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#publicationStatus");
	public static Etikett issnZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/ontology/bibo/issn");
	public static Etikett congressNumberZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#congressNumber");
	public static Etikett congressHostZF =
			ZettelHelper.etikett.getEtikett("http://hbz-nrw.de/regal#congressHost");
	public static Etikett otherZF = ZettelHelper.etikett
			.getEtikett("http://id.loc.gov/vocabulary/relators/oth");
	public static Etikett editorZF =
			ZettelHelper.etikett.getEtikett("http://purl.org/ontology/bibo/editor");
	public static Etikett institutionZF = ZettelHelper.etikett
			.getEtikett("http://dbpedia.org/ontology/institution");
	public static Etikett publicationYearZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#publicationYear");
	public static Etikett contributionZF =
			ZettelHelper.etikett.getEtikett("http://bibframe.org/vocab/contribution");
	public static Etikett affiliationZF =
			ZettelHelper.etikett.getEtikett("http://www.w3.org/2006/vcard/ns#adr");
	public static Etikett affiliationIndexZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#affiliationIndex");
	public static Etikett statusHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/statusHeader");
	public static Etikett titleHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/titleHeader");
	public static Etikett creatorshipHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/creatorshipHeader");
	public static Etikett resourceHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/resourceHeader");
	public static Etikett uploadHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/uploadHeader");
	public static Etikett catalogingHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/catalogingHeader");
	public static Etikett contributorsHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/contributorsHeader");
	public static Etikett identifiersHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/identifiersHeader");
	public static Etikett collectionHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/collectionHeader");
	public static Etikett publicationsHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/publicationsHeader");
	public static Etikett observationHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/observationHeader");
	public static Etikett collectionOneZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/collectionOne");
	public static Etikett fundingsHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/fundingsHeader");
	public static Etikett additionalMaterialZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/additionalMaterial");
	public static Etikett publisherVersionZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/publisherVersion");
	public static Etikett fulltextVersionZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/fulltextVersion");
	public static Etikett parallelEditionZF = ZettelHelper.etikett
			.getEtikett("http://hbz-nrw.de/regal#parallelEdition");
	public static Etikett typeZF = ZettelHelper.etikett
			.getEtikett("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	public static Etikett typeHeaderZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/typeHeader");
	public static Etikett collectionTwoZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/collectionTwo");
	public static Etikett internalReferenceZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/internalReference");
	public static Etikett additionalNotesZF = ZettelHelper.etikett
			.getEtikett("info:regal/zettel/additionalNotesHeader");
	public static Etikett ktblEmiMinZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/ktblEmiMin");
	public static Etikett livestockZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/livestock");
	public static Etikett lShandlingZF =
			ZettelHelper.etikett.getEtikett("info:regal/zettel/lShandling");

}
