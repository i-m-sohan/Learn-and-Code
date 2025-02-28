import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlogData {
	private String title;
    private String name;
    private String description;
    private int totalPosts;
    private Map<Integer,Set<String>> postNoWithImageUrls= new HashMap<>();
    
    public BlogData(String title, String name, String description, int totalPosts) {
    	this.title = title;
    	this.name = name;
    	this.description = description;
    	this.totalPosts = totalPosts;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public void setTotalPosts(int totalPost) {
    	this.totalPosts = totalPost;
    }    
    
    public void addImageUrls(Integer postNo, Set<String> imageUrls) {
    	if(!postNoWithImageUrls.containsKey(postNo)) {
    		postNoWithImageUrls.put(postNo, new HashSet<String>());
    	}
    	postNoWithImageUrls.get(postNo).addAll(imageUrls);
    }
    
    public String getTitle() {
    	return this.title;
    }
    
    public String getDescription(){
    	return this.description;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public Integer getTotalPosts() {
    	return this.totalPosts;
    }
    
    public Map<Integer, Set<String>> getPostNoWithImageUrls(){
    	return this.postNoWithImageUrls;
    }
}
