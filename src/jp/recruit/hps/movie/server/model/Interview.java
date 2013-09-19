package jp.recruit.hps.movie.server.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(name = "uR")
    private ModelRef<User> userRef = new ModelRef<User>(User.class);

    @Attribute(name = "cR")
    private ModelRef<Company> companyRef = new ModelRef<Company>(Company.class);

    @Attribute(name = "sD")
    private Date startDate;

    @Attribute(name = "eD")
    private Date endDate;

    @Attribute(name = "q")
    private String question;
    
    @Attribute(name = "a")
    private Integer atmosphere;

    @Attribute(name = "c")    
    public Category category;
    
    @Attribute(name = "r")
    private Result result;

    public enum Result {
        PASS, FAIL
    };

    public enum Category {
        INDIVIDUAL, GROUP, GROUP_DISCUSSION
    };

    /**
     * Returns the key.
     * 
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     * 
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Interview other = (Interview) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public ModelRef<User> getUserRef() {
        return userRef;
    }

    public ModelRef<Company> getCompanyRef() {
        return companyRef;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Integer atmosphere) {
        this.atmosphere = atmosphere;
    }

}
