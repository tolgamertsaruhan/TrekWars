package TrekWars;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {

	char[][] _map;

	
	
	
	public char[][] readMap() throws IOException
	{
		//prints the maze area
		 String[] mapFile = textReader("src//TrekWars//maze.txt");
		 char[][] map = new char[23][55];
		 for (int i = 0; i < 23;i++) {
			 for (int j = 0; j < 55;j++) {
				 switch(mapFile[i].charAt(j)) {
				 	case ' ':
				 		map[i][j] = ' ';
				 		break;
				 	case '#':
				 		map[i][j] = '#';
				 		break;
				 }
			 }

		 }
		 _map = map;
		 return map;
	}
	//for reading file
	public static String[] textReader(String filename) throws IOException {
        BufferedReader objReader_1 = new BufferedReader(new FileReader(filename));
        BufferedReader objReader_2 = new BufferedReader(new FileReader(filename));

        int counter = 0;
        String sentence;
        while ((sentence = objReader_1.readLine()) != null) 
        {
            if(!sentence.equals(""))
                counter++;
        }
        String[] data = new String[counter];
        sentence = "";
        int i = 0;
        while (counter > i) 
        {
            sentence = objReader_2.readLine();
            if(!sentence.equals("")) 
            {
                data[i] = sentence;
                i++;
            }
        }

        objReader_1.close();
        objReader_2.close();

        return data;
    }
	
	
	
}
