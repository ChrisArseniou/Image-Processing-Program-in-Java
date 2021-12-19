
package ce326.hw2;

public class UnsupportedFileFormatException extends java.lang.Exception {
    String msg = "Unsupported File Format";
    
    static final long serialVersionUID = -4567891456L;
   
    public UnsupportedFileFormatException(){
        super();
    }
    
    public UnsupportedFileFormatException(String msg){
        super(msg);
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return msg;
    }
}
