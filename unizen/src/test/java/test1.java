import org.apache.commons.codec.binary.Base64;

public class test1  {

public static void main(String[] args) {
	
        byte[] decoded=Base64.decodeBase64("AAUMBAAAAP8AAAEAAAABAQAAAQIAAAEDAAABBAAAAQUAAAEGAAABBwAAAQgAAAEJAAABCg==");
        for(Byte b: decoded){
        	//System.out.println("decoded val : " + b);
        	String b1=String.format("%x", b);
        	//System.out.println("decoded val : " + b1);
        	System.out.println("hex val val : " + Integer.parseInt(b1,16));
        	//String res=Integer.toBinaryString(Integer.parseUnsignedInt(waterLtr));
			//String resultant=String.valueOf(Integer.parseUnsignedInt(res,2));
        	
        }
        
		/*int n=0;
		try{
			n=Byte.valueOf(hex);
			System.out.println("Loraa  " +n);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Lora1  " +n);*/
    
    
}


}