package base.brian.com.baseapp_mvp.db;


import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by brian on 16/6/5.
 */
public class AppDbManager {
    public static final String TAG = AppDbManager.class.getSimpleName();
    private static AppDbManager instance;
    private DbManager db;
    public AppDbManager() {
        db = x.getDb(daoConfig);
    }
    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("brian.db")
//            .setDbDir(new File(ISATAppConfig.APP_SDCARD_BASE_PATH)) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {


                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    LogUtil.i("onDbOpened");

                }
            }).setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    LogUtil.i("onDbOpened");

                }
            }).setTableCreateListener(new DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(DbManager db, TableEntity<?> table) {
                    LogUtil.i("onTableCreated tablename:"+table.getName());
                }
            });

    /**
     * 获取数据库对象
     */
    public static AppDbManager getInstance() {
        if (null == instance) {
            instance = new AppDbManager();
        }
        return instance;
    }



}
