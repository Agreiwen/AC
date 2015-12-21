package ac.partie2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

public class Partie2 {
	
	public Partie2() throws IOException{
		
		String defaultDirectory = "src/ac/partie2/tests";
		JFileChooser chooser = new JFileChooser(defaultDirectory);
	    
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       String file = chooser.getSelectedFile().getPath();
	       System.out.println(file);
	       System.out.println("Fingerprint : "+fingerprint(5407,file));
	    }
	}
	
	public int multiply(int x, int y, int p){
		int result = 0;
		while(y>0){
			if((y & 1) == 1) result = (result+x)%p;
			y >>= 1;
			x = (2*x)%p;
		}
		return result;
	}
	
	public int puissance(int x, int k, int p){
		int res = 1;
		for(int i = 1; i <= k; i++){
			res = (multiply(res,x,p))%p;
		}
		return res;
	}
	
	public boolean pseudoprime(int p){
		boolean res = puissance(2,p-1,p) == 1;
		return res;
	}
	
	public int nextprime(){
		int lower = 2;
		int higher = (int) Math.pow(2, 23);
		int random = (int)(Math.random() * (higher-lower)) + lower;
		while(pseudoprime(random) == false){
			random = (int)(Math.random() * (higher-lower)) + lower;
		}
		return random;
	}
	
	public int puissance2(int x, int k, int p){
        int res = 1;
        for (int i = 1; i <= k; i++) {
            res = (res*x)%p;  
        }
        return res%p;
        
    }
	
	public int multiplicationModulaire(int a, int b, int c){
		int res = (a%c * b%c)%c;
		return res;
	}
	
	public int additionModulaire(int a, int b, int c){
		int res = (a%c + b%c)%c;
		return res;
	}
	
	public int fingerprint(int p, String fn) throws IOException{
		Path path = Paths.get(fn);
		byte[] data = Files.readAllBytes(path);
		

		int[] tab = new int[data.length];
		System.out.println("Taille tab : "+tab.length);
		for(int i = 0; i < data.length; i++){
			tab[i] = BytetoUnsignedInt(data[i]);
		}
		
		int taille = tab.length;
		int res = 0;
		int aux;
		for(int i=0; i<taille; i++){
			//System.out.println(i);
			aux = multiplicationModulaire(tab[i], puissance2(256, taille-1-i, p), p);
			res = additionModulaire(res, aux, p);
		}
		return res;
	}
	
	public static int BytetoUnsignedInt(byte b) {
		return b & 0xFF;
	}
	
	public static void main(String[] args) {
		try {
			new Partie2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
