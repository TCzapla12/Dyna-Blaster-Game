package com.DyniaServer.main;
import java.io.*;


public final class ServerCommands {

	public static String serverAction(String command) throws IOException {
        String serverCommand = command;
        String originalCommand= command;
        System.out.println(command);
        if(command.contains("GET_LEVELS:")){
            originalCommand=command;
            serverCommand=("GET_LEVELS");
        }
        if(command.contains("GET_RESULTS:")){
            originalCommand=command;
            serverCommand="GET_RESULTS";
        }
        if(command.contains("GET_CONFIG:")){
            originalCommand=command;
            serverCommand="GET_CONFIG";
        }
        if(command.contains("GET_SAVE:")){
            originalCommand=command;
            serverCommand="GET_SAVE";
        }
		if(command.contains("GAME_SAVE:")){
            originalCommand=command;
            serverCommand="GAME_SAVE";
        }
		if(command.contains("GAME_SCORE:")){
            originalCommand=command;
            serverCommand="GAME_SCORE";
        }
        String serverMessage;
        switch (serverCommand){
            case "GET_RESULTS":
                serverMessage=getResults();
                break;
            case "GET_LEVELS":
                serverMessage=getLevels();
                break;
            case "GET_CONFIG":
                serverMessage=getConfig();
                break;
            case "GET_SAVE":
                serverMessage=getSave();
                break;
            case "GAME_SAVE":
                String[] split = originalCommand.split(":");
                String[] split2 = split[1].split("#");
                serverMessage=gameSave(split2);
				break;
            case "GAME_SCORE":
                String[] split3 = originalCommand.split(":");
                serverMessage=gameScore(split3[1]);
				break;
            default:
                serverMessage="INVALID_COMMAND";
        }
        return serverMessage;
    }

   private static String getConfig(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/config.txt"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine+"#");
            }
        }
        catch (Exception e){
        }
        return sb.toString();
    }
    private static String getResults(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/results.txt"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine+"#");
            }
        }
        catch (Exception e){

        }
        return sb.toString();
    }
    private static String getSave(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/save.txt"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine+"#");
            }
        }
        catch (Exception e){

        }
        return sb.toString();
    }

    private static String getLevels(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/level.txt"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine+"#");
            }
        }
        catch (Exception e){

        }
        return sb.toString();
    }
    private static String gameSave(String[] split2) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter("resources/save.txt"));
        br.write(split2[0]+"\n");
        br.write(split2[1]+"\n");
        br.write(split2[2]+"\n");
        br.write(split2[3]+"\n");
        br.close();
        return "UPLOADED SUCCESSFULLY";
    }
    private static String gameScore(String split1) throws IOException
    {
        String[] element = split1.split("@");
        List.getScore(element[0], Integer.valueOf(element[1]));
        BufferedWriter br = new BufferedWriter(new FileWriter("resources/results.txt"));
        for(int i = 0; i< List.getSize(); i++)
        {
            String elemen = List.getString(i)+"\n";
            br.write(elemen);
        }
        br.close();
        return "UPLOADED SUCCESSFULLY";
    }
}
