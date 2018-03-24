package ai.elimu.appstore.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import ai.elimu.appstore.dao.converter.CalendarConverter;
import ai.elimu.appstore.model.Application;
import java.util.Calendar;

import ai.elimu.appstore.model.ApplicationVersion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APPLICATION_VERSION".
*/
public class ApplicationVersionDao extends AbstractDao<ApplicationVersion, Long> {

    public static final String TABLENAME = "APPLICATION_VERSION";

    /**
     * Properties of entity ApplicationVersion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ApplicationId = new Property(1, long.class, "applicationId", false, "APPLICATION_ID");
        public final static Property FileSizeInKb = new Property(2, Integer.class, "fileSizeInKb", false, "FILE_SIZE_IN_KB");
        public final static Property FileUrl = new Property(3, String.class, "fileUrl", false, "FILE_URL");
        public final static Property ChecksumMd5 = new Property(4, String.class, "checksumMd5", false, "CHECKSUM_MD5");
        public final static Property ContentType = new Property(5, String.class, "contentType", false, "CONTENT_TYPE");
        public final static Property VersionCode = new Property(6, Integer.class, "versionCode", false, "VERSION_CODE");
        public final static Property VersionName = new Property(7, String.class, "versionName", false, "VERSION_NAME");
        public final static Property StartCommand = new Property(8, String.class, "startCommand", false, "START_COMMAND");
        public final static Property TimeUploaded = new Property(9, long.class, "timeUploaded", false, "TIME_UPLOADED");
    }

    private DaoSession daoSession;

    private final CalendarConverter timeUploadedConverter = new CalendarConverter();

    public ApplicationVersionDao(DaoConfig config) {
        super(config);
    }
    
    public ApplicationVersionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPLICATION_VERSION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"APPLICATION_ID\" INTEGER NOT NULL ," + // 1: applicationId
                "\"FILE_SIZE_IN_KB\" INTEGER NOT NULL ," + // 2: fileSizeInKb
                "\"FILE_URL\" TEXT NOT NULL ," + // 3: fileUrl
                "\"CHECKSUM_MD5\" TEXT NOT NULL ," + // 4: checksumMd5
                "\"CONTENT_TYPE\" TEXT NOT NULL ," + // 5: contentType
                "\"VERSION_CODE\" INTEGER NOT NULL ," + // 6: versionCode
                "\"VERSION_NAME\" TEXT," + // 7: versionName
                "\"START_COMMAND\" TEXT," + // 8: startCommand
                "\"TIME_UPLOADED\" INTEGER NOT NULL );"); // 9: timeUploaded
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APPLICATION_VERSION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ApplicationVersion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getApplicationId());
        stmt.bindLong(3, entity.getFileSizeInKb());
        stmt.bindString(4, entity.getFileUrl());
        stmt.bindString(5, entity.getChecksumMd5());
        stmt.bindString(6, entity.getContentType());
        stmt.bindLong(7, entity.getVersionCode());
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(8, versionName);
        }
 
        String startCommand = entity.getStartCommand();
        if (startCommand != null) {
            stmt.bindString(9, startCommand);
        }
        stmt.bindLong(10, timeUploadedConverter.convertToDatabaseValue(entity.getTimeUploaded()));
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ApplicationVersion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getApplicationId());
        stmt.bindLong(3, entity.getFileSizeInKb());
        stmt.bindString(4, entity.getFileUrl());
        stmt.bindString(5, entity.getChecksumMd5());
        stmt.bindString(6, entity.getContentType());
        stmt.bindLong(7, entity.getVersionCode());
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(8, versionName);
        }
 
        String startCommand = entity.getStartCommand();
        if (startCommand != null) {
            stmt.bindString(9, startCommand);
        }
        stmt.bindLong(10, timeUploadedConverter.convertToDatabaseValue(entity.getTimeUploaded()));
    }

    @Override
    protected final void attachEntity(ApplicationVersion entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ApplicationVersion readEntity(Cursor cursor, int offset) {
        ApplicationVersion entity = new ApplicationVersion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // applicationId
            cursor.getInt(offset + 2), // fileSizeInKb
            cursor.getString(offset + 3), // fileUrl
            cursor.getString(offset + 4), // checksumMd5
            cursor.getString(offset + 5), // contentType
            cursor.getInt(offset + 6), // versionCode
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // versionName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // startCommand
            timeUploadedConverter.convertToEntityProperty(cursor.getLong(offset + 9)) // timeUploaded
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ApplicationVersion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setApplicationId(cursor.getLong(offset + 1));
        entity.setFileSizeInKb(cursor.getInt(offset + 2));
        entity.setFileUrl(cursor.getString(offset + 3));
        entity.setChecksumMd5(cursor.getString(offset + 4));
        entity.setContentType(cursor.getString(offset + 5));
        entity.setVersionCode(cursor.getInt(offset + 6));
        entity.setVersionName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStartCommand(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTimeUploaded(timeUploadedConverter.convertToEntityProperty(cursor.getLong(offset + 9)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ApplicationVersion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ApplicationVersion entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ApplicationVersion entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getApplicationDao().getAllColumns());
            builder.append(" FROM APPLICATION_VERSION T");
            builder.append(" LEFT JOIN APPLICATION T0 ON T.\"APPLICATION_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ApplicationVersion loadCurrentDeep(Cursor cursor, boolean lock) {
        ApplicationVersion entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Application application = loadCurrentOther(daoSession.getApplicationDao(), cursor, offset);
         if(application != null) {
            entity.setApplication(application);
        }

        return entity;    
    }

    public ApplicationVersion loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ApplicationVersion> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ApplicationVersion> list = new ArrayList<ApplicationVersion>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ApplicationVersion> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ApplicationVersion> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
