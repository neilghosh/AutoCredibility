/**
 * Copied from and modified from the Sentiment Analysis Demo 
 * available at http://alias-i.com/lingpipe/demos/tutorial/sentiment/read-me.html
 * 
 * Class used: PolarityBasic.java and Models.java
 */

package analyzer;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class SentimentAnalyzer {

    File mPolarityDir;
    File polarityModelFile;
    String[] mCategories;
    int nGram;
    LMClassifier lmClassifier;
    
    public static final int POLARITY_POSITIVE = 1;
    public static final int POLARITY_NEGATIVE = -1;
    public static final int POLARITY_UNDEFINED = 0;

    public SentimentAnalyzer(){
    	this("D:\\Program_Files\\lingpipe-3.8.1\\demos\\data\\review_polarity\\txt_sentoken");
    }
    
    public SentimentAnalyzer(String trainingFilesDir) {
        System.out.println("\nBASIC POLARITY DEMO");

        mPolarityDir = new File(trainingFilesDir);//new File("D:\\Program_Files\\lingpipe-3.8.1\\demos\\data\\review_polarity","txt_sentoken");
        polarityModelFile = new File("data\\lingpipe_compiled_polarity_file.model");
        
        System.out.println("\nData Directory=" + mPolarityDir);
        mCategories = mPolarityDir.list();
        nGram = 8;
    }

    void run() throws ClassNotFoundException, IOException {
        evaluateAndCheck(train());
    }
    
    public void trainAndSaveModel() throws IOException, ClassNotFoundException{
    	saveModel(train());
    }
    
    void saveModel(DynamicLMClassifier<NGramProcessLM> mClassifier){
        System.out.println("Compiling Polarity Model to File=" + polarityModelFile);
        try {
			AbstractExternalizable.compileTo(mClassifier,polarityModelFile);
			//classifier2.compileTo(new ObjectOutputStream(new FileOutputStream(polarityModelFile)));
			System.out.println("\nDONE.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void loadModel(){
    	System.out.println("Loading trained model from file=" + polarityModelFile + " ...");
        try {
			lmClassifier = (LMClassifier)AbstractExternalizable.readObject(polarityModelFile);	        
	        System.out.println("Done.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    DynamicLMClassifier<NGramProcessLM> train() throws IOException, ClassNotFoundException {
    	DynamicLMClassifier<NGramProcessLM> mClassifier;
    	mClassifier 
        	= DynamicLMClassifier.createNGramProcess(mCategories,nGram);
    	
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        System.out.println("\nTraining.");
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                ++numTrainingCases;
                String review = Files.readFromFile(trainFile,"ISO-8859-1");
                numTrainingChars += review.length();
                mClassifier.train(category,review);
            }
        }
        System.out.println("  # Training Cases=" + numTrainingCases);
        System.out.println("  # Training Chars=" + numTrainingChars);
           
        return mClassifier;
    }
   
    
    public int getPolarityValue(String text) throws IOException {
        String polarityCategory = evaluate(text);
        
        if("pos".equalsIgnoreCase(polarityCategory))
            return SentimentAnalyzer.POLARITY_POSITIVE;
        else if("neg".equalsIgnoreCase(polarityCategory))
           return SentimentAnalyzer.POLARITY_NEGATIVE;
        return SentimentAnalyzer.POLARITY_UNDEFINED;
    }
    
    private String evaluate(String text) throws IOException {
        System.out.println("\nEvaluating.");
        
        System.out.println("\n\n");
        Classification classification
            = lmClassifier.classify(text);
        String polarityCategory = classification.bestCategory();
        System.out.println("\nPolarity found: " + polarityCategory);
        return polarityCategory;
    }
    
    private static String getFullCategory(String polarityCategory) {
    	if("pos".equalsIgnoreCase(polarityCategory))
            return "Positive";
    	else if("neg".equalsIgnoreCase(polarityCategory))
           return "Negative";
        return "Undefined";
    }
    
    boolean isTrainingFile(File file) {
        return file.getName().charAt(2) != '9';  // test on fold 9
    }
    
    /**
     * Original evaluate() method
     * @throws IOException
     */
    void evaluateAndCheck(DynamicLMClassifier<NGramProcessLM> mClassifier) throws IOException {
        System.out.println("\nEvaluating.");
        int numTests = 0;
        int numCorrect = 0;
        
        System.out.println("\n\n");
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                if (!isTrainingFile(trainFile)) {
                    String review = Files.readFromFile(trainFile,"ISO-8859-1");
                    ++numTests;
                    Classification classification
                        = mClassifier.classify(review); //mClassifier.classify(review);
                    if (classification.bestCategory().equals(category)) 
                        ++numCorrect;
                    
                    System.out.println("The " + getFullCategory(category) + " Review '" + 
                            trainFile.getName() + "' is classified as => '" +  
                            getFullCategory(classification.bestCategory()) + "'");    
                }
            }
        }
        System.out.println("\n\n  # Test Cases=" + numTests);
        System.out.println("  # Correct=" + numCorrect);
        System.out.println("  % Correct=" 
                           + ((double)numCorrect)/(double)numTests);
    }

    
    public static void main(String[] args) {
        try {
            SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer(
            		"D:\\Program_Files\\lingpipe-3.8.1\\demos\\data\\review_polarity\\txt_sentoken");
            
            sentimentAnalyzer.loadModel();
            
            String text = "Marriage is bad, very bad. Because it needs tremendous compromise! " +
            		"Bachelorhood is far better. Do not marry, to enjoy life to the fullest!" +
            		"Marriage sucks! It is the worst thing to happen in life!";
            System.out.println("Sentiment Value = " + sentimentAnalyzer.getPolarityValue(text));
            
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }

}


