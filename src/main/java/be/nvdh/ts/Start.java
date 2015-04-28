package be.nvdh.ts;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class Start {

	private static final String DATEFORMAT = "yyyyMMdd";

	public static void main(String[] args) throws Exception {
		
		Options options = initOptions();
		CommandLine commandLine = getCommandLine(args, options);
		
		processHelp(options, commandLine);
		process(commandLine);
	}

	private static void process(CommandLine cmd) {
		if (!cmd.hasOption("c")){
			System.out.println("No configuration file provided. Please add configuration file with -c. See help for more options.");
		} else {
			String configurationFile = cmd.getOptionValue("c");
			Date date = getDate(cmd);
			System.out.println("Using configuration file: " + configurationFile);
			System.out.println("Using date: " + date);
			new Warrior(configurationFile, date).start();
		}
	}

	private static Date getDate(CommandLine cmd) {
		String date = cmd.getOptionValue("d");
		if (StringUtils.isEmpty(date)){
			return new Date();
		}else {
			try {
				return new SimpleDateFormat(DATEFORMAT).parse(date);
			} catch (java.text.ParseException e) {
				throw new IllegalArgumentException("Error parsing date, please use pattern: " + DATEFORMAT);
			}
		}
	}

	private static void processHelp(Options options, CommandLine cmd) {
		if (cmd.hasOption("h")){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ts", options );
			System.exit(0);
		}
	}

	private static Options initOptions() {
		Options options = new Options();
		options.addOption("h", "help", false, "Print this help message.");
		options.addOption("c", "config", true, "The JSON configuration file.");
		options.addOption("d", "date", true, "The date to fetch. The result depends on the fetcher. Usually the month is taken.");
		return options;
	}
	
	private static CommandLine getCommandLine(String[] args, Options options) throws ParseException {
		return new GnuParser().parse(options, args);
	}

}
