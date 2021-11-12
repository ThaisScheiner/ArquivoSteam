package controller;

import java.io.IOException;

public interface SteamI
{
	 public void createFile (int ano, String mes, String path, String name) throws IOException;
	    public void readFile (int ano, String mes, double media,String path, String name) throws IOException;
}
