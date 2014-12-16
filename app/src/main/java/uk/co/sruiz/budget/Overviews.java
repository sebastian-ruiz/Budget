package uk.co.sruiz.budget;


import java.util.ArrayList;
import java.util.Map;


/**
 * Party object that is core object for application
 * @author Sebastian Ruiz
 */
public class Overviews {
    /**
     * This is a reference to the hashmap object in our "data store"
     * so we can look up fields dynamically
     */
    private Map<String, Object> mFields;

    /** This is our object ID */
    private String mDocId;

    private String mCurrentOverview;

    /** Last state of current user ID so we can figure out if we need to refresh fields */
    private String mLastMyUserId;

    /**
     * Gets Meteor object ID
     * @return object ID string
     */
    public String getId() {
        return mDocId;
    }
    /**
     * Gets title for party
     * @return title of party
     */
    public String getTitle() {
        return ((String) mFields.get("title"));
    }
    /**
     * Gets description for party
     * @return description of party
     */
    public String getDescription() {
        return ((String) mFields.get("description"));
    }
    /**
     * joined array of userIDs
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getJoinedBy() {
        return ((ArrayList<String>) mFields.get("joinedBy"));
    }

    /**
     * Gets GPS longitude for party
     * @return longitude
     */
    public String getName() {
        return ((String) mFields.get("name"));
    }
    /**
     * Gets GPS longitude for party
     * @return longitude
     */
    public String getAdmin() {
        return ((String) mFields.get("admin"));
    }
    /**
    /**
     * Whether party is public
     * @return true if public
     */
    public boolean isPublic() {
        return ((Boolean) mFields.get("public"));
    }

    /**
     * Constructor for Party object
     * @param docId Meteor's object ID for this Party
     * @param fields field/value map reference
     */
    public Overviews(String docId, Map<String, Object> fields) {
        this.mFields = fields;
        this.mDocId = docId;
        refreshFields();
    }

    /**
     * Refreshes any fields dependent on login
     */
    private void refreshFieldsIfIdChanged() {
        if (hasUserIdChanged()) {
            refreshFields();
        }
    }
    /**
     * used to figure out if user ID has changed so we can refresh user ID dependent fields
     * @return true if changed
     */
    private boolean hasUserIdChanged() {
        String myUserId = MyDDPState.getInstance().getUserId();
        if (((myUserId == null) && (mLastMyUserId != null))
                || ((myUserId != null) && (mLastMyUserId == null))
                || (!myUserId.equals(mLastMyUserId))) {
            return true;
        }
        return false;
    }

    /**
     * This recalculates any internal fields that would take a long time
     * to calculate/get if we had to reparse the the fields map.
     * Currently only gets the myRsvp and attendees and isOwner fields.
     * NOTE: This also needs to be called when the underlying data is changed by DDP.
     */
    public void refreshFields() {
        String myUserId = MyDDPState.getInstance().getUserId();
    }

    /**
     * Override so we can print party info easily
     */
    @Override
    public String toString() {
        return getTitle();
    }
}


