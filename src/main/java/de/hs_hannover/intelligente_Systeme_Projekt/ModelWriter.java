package de.hs_hannover.intelligente_Systeme_Projekt;

import org.apache.jena.rdf.model.*;

public class ModelWriter {
    public static Model WriteModel() {
        String foaf = "http://xmlns.com/foaf/0.1/";
        String bio = "http://purl.org/vocab/bio/0.1/";
        String dc = "http://purl.org/dc/elements/1.1/";
        String xsd = "https://www.w3.org/2001/XMLSchema#";
        String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        String sf = "http://starfleet.db/#";
        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
        String geo = "http://sws.geonames.org/#";

        Model model = ModelFactory.createDefaultModel();

        Resource picard = model.createResource(sf + "Jean-Luc_Picard");
        Resource picard_enrollment = model.createResource(sf + "Jean-Luc_Picard_Enrollment");
        Resource william_birth = model.createResource(sf + "William_Riker_Birth");
        Resource william = model.createResource(sf + "William_Riker");
        Resource kyle = model.createResource(sf + "Kyle_Riker");
        Resource betty = model.createResource(sf + "Betty_Riker");
        Resource deanna = model.createResource(sf + "Deanna_Troi");
        Resource starfleet = model.createResource(sf + "Starfleet");
        Resource organization = model.createResource(foaf + "Organization");
        Resource person = model.createResource(foaf + "Person");
        Resource enrollment = model.createResource(bio + "Enrollment");
        Resource alaska = model.createResource(geo + "5879092");
        Resource birth = model.createResource(bio + "Birth");
        Resource property = model.createResource(rdf + "Property");
        Resource knows = model.createResource(sf + "knows");
        Resource resource = model.createResource(rdfs + "Resource");
        Resource string = model.createResource(xsd + "string");

        Property firstName = model.createProperty(foaf + "firstName");
        Property familyName = model.createProperty(foaf + "familyName");
        Property name = model.createProperty(foaf + "name");
        Property title = model.createProperty(foaf + "title");
        Property dienstnummer = model.createProperty(sf + "Dienstnummer");
        Property member = model.createProperty(foaf + "member");
        Property date = model.createProperty(dc + "firstName");
        Property principal = model.createProperty(bio + "principal");
        Property parent = model.createProperty(bio + "parent");
        Property place = model.createProperty(bio + "place");
        Property type = model.createProperty(rdf + "type");
        Property employer = model.createProperty(bio + "employer");
        Property father = model.createProperty(bio + "father");
        Property mother = model.createProperty(bio + "mother");
        Property partner = model.createProperty(bio + "partner");
        Property domain = model.createProperty(rdfs + "domain");
        Property range = model.createProperty(rdfs + "range");
        Property datatype = model.createProperty(rdfs + "datatype");
        Property subProperty = model.createProperty(rdfs + "subPropertyOf");

        Statement statements[] = new Statement[] {
                model.createStatement(picard, type, person),
                model.createStatement(picard, firstName, model.createLiteral("Jean-Luc")),
                model.createStatement(picard, familyName, model.createLiteral("Picard")),
                model.createStatement(picard, name, model.createLiteral("Jean-Luc Picard")),
                model.createStatement(picard, title, model.createLiteral("Captain")),
                model.createStatement(picard, dienstnummer, model.createLiteral("SP-937-215")),
                model.createStatement(picard, member, starfleet),

                model.createStatement(picard_enrollment, type, enrollment),
                model.createStatement(picard_enrollment, date, model.createLiteral("2322")),
                model.createStatement(picard_enrollment, principal, picard),

                model.createStatement(william_birth, type, birth),
                model.createStatement(william_birth, date, model.createLiteral("2335-04-15")),
                model.createStatement(william_birth, principal, william),
                model.createStatement(william_birth, parent, william),
                model.createStatement(william_birth, parent, betty),
                model.createStatement(william_birth, place, alaska),

                model.createStatement(william, type, person),
                model.createStatement(william, name, model.createLiteral("William Thomas Riker")),
                model.createStatement(william, member, starfleet),

                model.createStatement(kyle, type, person),
                model.createStatement(betty, type, person),
                model.createStatement(deanna, type, person),

                model.createStatement(starfleet, employer, william),
                model.createStatement(kyle, father, william),
                model.createStatement(betty, mother, william),
                model.createStatement(kyle, type, person),
                model.createStatement(william, partner, deanna),
                model.createStatement(deanna, partner, william),
                model.createStatement(starfleet, type, organization),

                model.createStatement(knows, type, property),
                model.createStatement(knows, domain, person),
                model.createStatement(knows, range, resource),

                model.createStatement(dienstnummer, type, property),
                model.createStatement(dienstnummer, datatype, string),
                model.createStatement(dienstnummer, domain, person),
                model.createStatement(dienstnummer, subProperty, knows),
        };

        model.add(statements);

        return model;
    }
}
