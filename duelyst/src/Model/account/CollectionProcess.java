package Model.account;

import com.gilecode.yagson.YaGson;

public class CollectionProcess {
    private Collection collection;
    private  Collection tempCollection;

    public CollectionProcess(Collection collection){
        this.setCollection(collection);
        YaGson gson = new YaGson();
        this.tempCollection = gson.fromJson(gson.toJson(collection), Collection.class);
    }

    public Collection getTempCollection() {
        return tempCollection;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
