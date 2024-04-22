# Example for Data Directory Structure

```plain text
data/
├─ local/
│ ├─ entities/
│ │ ├─ AuthorEntity
│ ├─ dao/
│ ├─ NiADatabase
├─ network/
│ ├─ NiANetwork
│ ├─ models/
│ │ ├─ NetworkAuthor
├─ model/
│ ├─ Author
├─ repository/
```

# More About

Please read [Android Doc](https://developer.android.com/topic/architecture/data-layer/offline-first) on how to structure the data layer.

## Notice

In this Doc, it is using flow (a kotlin feature), we should use liveData (or if it will make us lose mark, we can implement our own observer base class)
