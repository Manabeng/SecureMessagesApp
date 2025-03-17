/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.tut.encryption;

/**
 *
 * @author <Jeffrey Nkadimeng 231983031>
 */
public class MessageEncryptor {
    private String msg;

    public MessageEncryptor(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String encryptMessage(String msg){
            int increment = 3;
            char letters[] =new char[msg.length()];
	    String encryptedMessage = "";
		
	    for(int x=0; x<msg.length() ;x++)
	    {
		letters[x] =msg.charAt(x);
                if (Character.isUpperCase(letters[x])) {
                    letters[x] = (char) (((letters[x] - 'A' + increment) % 26) + 'A');
                }
                else if(Character.isLowerCase(letters[x]))
                {
                    letters[x] = (char) (((letters[x] - 'a' + increment) % 26) + 'a');
		}
		encryptedMessage += letters[x];
            }
            
            return encryptedMessage;
			
        }
}
