package de.hs_hannover.intelligente_Systeme_Projekt;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.FBRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ReadTurtle {
	
	static String sampleQueryName = 
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			"SELECT ?name " + 
			"WHERE {" +
				"?person foaf:name ?name ." + 
			"}";
	
	// Group Graph Pattern
	// Get URI of Person and Name if available
	static String groupSample = 
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			"SELECT ?person ?name " + 
			"WHERE { " + 
			    "?person a foaf:Person . " + 
			    "OPTIONAL { ?person foaf:name ?name } " +
			"}";
	
	// filters to define constraints
	// Get person name start with J
	static String constraintsSample = 
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			"PREFIX bio: <http://purl.org/vocab/bio/0.1/> " +
			"SELECT ?name " + 
			"WHERE { " +
				"?person foaf:name ?name . " + 
				"FILTER EXISTS { { ?p1 bio:partner ?person } UNION { ?person bio:partner ?p1 } } " +
			"}";
	
	//# Ask 
	//# Ask if Picard knows his Dienstnummer
	static String askSample =
			"PREFIX sf: <http://starfleet.db/#> " +
			"ASK { " +
			  "sf:Jean-Luc_Picard sf:knows \"SP-937-215\". " +
			"}";

	public static void main(String[] args) throws FileNotFoundException {
		FileManager.get().addLocatorClassLoader(ReadTurtle.class.getClassLoader());
		Model model = FileManager.get().loadModel("own.ttl", null, "TURTLE");

		//model = ModelWriter.WriteModel();

		Reasoner reasoner;

		reasoner = new GenericRuleReasoner(Rule.rulesFromURL("jena.rules"));
		reasoner.setDerivationLogging(true);
		((FBRuleReasoner)reasoner).setTraceOn(true);
		model = ModelFactory.createInfModel(reasoner, model);

		reasoner = ReasonerRegistry.getRDFSSimpleReasoner();
        model = ModelFactory.createInfModel(reasoner, model);

        reasoner = ReasonerRegistry.getRDFSReasoner();
		model = ModelFactory.createInfModel(reasoner, model);

        reasoner = ReasonerRegistry.getTransitiveReasoner();
		model = ModelFactory.createInfModel(reasoner, model);

		model.write(new FileOutputStream("out.txt"), "TURTLE");
        ModelWriter.WriteModel().write(new FileOutputStream("out2.txt"), "TURTLE");

        //PrintModel(inf);


		RunQuery(sampleQueryName, model);
		RunQuery(groupSample, model, "person", "name");
		RunQuery(constraintsSample, model, "name");
		RunAsk(askSample, model);
	}
	
	static void RunAsk(String s, Model m) {
		Query query = QueryFactory.create(s);
		try (QueryExecution qexec = QueryExecutionFactory.create(query, m)) {
			boolean results = qexec.execAsk();
			System.out.println("Result: " + results);
		}
	}
	
	static void RunQuery(String s, Model m, String... p) {
	    Query query = QueryFactory.create(s);
	    try (QueryExecution qexec = QueryExecutionFactory.create(query, m)) {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	            QuerySolution soln = results.nextSolution();
	            for (String string : p) {
	            	RDFNode result = soln.get(string);
		            System.out.println(string + ": " + result);	
				}
	        }
	    }
	}
	
	static void PrintModel(Model m){
		StmtIterator iter = m.listStatements();
		try {
			while (iter.hasNext()) {
				Statement stmt = iter.next();

				Resource s = stmt.getSubject();
				Resource p = stmt.getPredicate();
				RDFNode o = stmt.getObject();

				System.out.println("Subject: " + s);
				System.out.println("Predicate: " + p);
				System.out.println("Object: " + o);
			}
		} finally {
			if (iter != null)
				iter.close();
		}
		
	}

}
