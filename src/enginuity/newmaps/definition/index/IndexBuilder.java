package enginuity.newmaps.definition.index;

import enginuity.newmaps.xml.SaxParserFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static enginuity.util.MD5Checksum.getMD5Checksum;
import enginuity.util.exception.NameableNotFoundException;

public class IndexBuilder {
        
    public static final String INDEX_FILE_NAME = "index.dat";
    public static final String MEMMODEL_FILE_NAME = "memmodels.xml";
    
    private File dir;
    private Index index;
    
    public IndexBuilder(File dir, Index index) {
        this.dir = dir;          
        this.index = index;
        
        // Clean up existing defs
        index.cleanup();
        
        // Process all definition files
        traverse(dir);      
        
        // Output index
        save(index, dir);        
    }
    
    private void traverse(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i=0; i<children.length; i++) {
                traverse(new File(file, children[i]));
            }
        } else {          
            processFile(file);
        }
    }
    
    private void processFile(File file) {
        if (!file.getName().equalsIgnoreCase(INDEX_FILE_NAME) && 
            !file.getName().equalsIgnoreCase(MEMMODEL_FILE_NAME)) {
            
            IndexItem idxItem = null;            
            try {
                idxItem = index.get(file);
            } catch (NameableNotFoundException ex) {
                idxItem = new IndexItem();
            }
            
            if (!index.fileCurrent(file, idxItem)) {
                
                try {               

                    IndexHandler handler = new IndexHandler();
                    SaxParserFactory.getSaxParser().parse(new BufferedInputStream(new FileInputStream(file)), handler); 
                    IndexItem item = handler.getItem();
                    item.setFile(file);
                    index.add(item);

                } catch (Exception ex) {
                    // TODO: Handle exceptions
                    ex.printStackTrace();
                }
            }
        }        
    }    
    
    private static void save(Index index, File file) {

        index.fixInheritance();        
        try {                       
            FileOutputStream fos = new FileOutputStream(file + "/" + INDEX_FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(index);
            oos.flush();
            oos.close();
            
        } catch (Exception ex) {
            // TODO: Exception handling
            ex.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        try {
            File file = new File("/newdefs");
            IndexBuilder b = new IndexBuilder(file, IndexUtil.getIndex(file));
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    } 
}