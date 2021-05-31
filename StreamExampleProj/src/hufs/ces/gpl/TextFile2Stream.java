package hufs.ces.gpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TextFile2Stream {

	static final String delimPattern = "(\\s|\\p{Punct}|’)+";

	static Set<String> filterSet = null;
	static {
		try {
			URI stopWordURI = TextFile2Stream.class.getResource("/english.txt").toURI();
			filterSet = Files.lines(Paths.get(stopWordURI),Charset.defaultCharset())
					.map(s->s.trim())
					.map(s->s.toLowerCase())
					.collect(Collectors.toSet());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println(filterSet);
		
		URL url = TextFile2Stream.class.getResource("/Alcott-Little.txt");
		System.out.println(url);
		Stream wordStream = null;
		
		try {
			wordStream = readFileToWordStream(new File(url.toURI()));
			wordStream
			.filter(s->!filterSet.contains(s))
			.limit(10000).forEach(s->{
				System.out.println(s);
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
		

	}
	public static Stream<String> readFileToWordStream(File file) {
		Stream<String> words = null;
		try {
			words = Files.lines(Paths.get(file.toURI()),Charset.defaultCharset())
					.filter(s->s.length()>0)
					.map(s->s.trim())
					.map(lin->lin.split(delimPattern))
					.flatMap(s->Arrays.stream(s))
					.map(s->s.trim())
					.filter(s->s.matches("\\p{Alpha}+"))
					.map(s->s.toLowerCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}

}
