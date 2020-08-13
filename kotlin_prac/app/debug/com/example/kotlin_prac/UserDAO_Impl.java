package com.example.kotlin_prac;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDAO_Impl implements UserDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserVO;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUserVO;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUserVO;

  public UserDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserVO = new EntityInsertionAdapter<UserVO>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UserVO`(`id`,`title`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserVO value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
      }
    };
    this.__deletionAdapterOfUserVO = new EntityDeletionOrUpdateAdapter<UserVO>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UserVO` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserVO value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUserVO = new EntityDeletionOrUpdateAdapter<UserVO>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UserVO` SET `id` = ?,`title` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserVO value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        stmt.bindLong(3, value.getId());
      }
    };
  }

  @Override
  public void insert(final UserVO uservo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserVO.insert(uservo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final UserVO uservo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserVO.handle(uservo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final UserVO uservo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserVO.handle(uservo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<UserVO>> getAll() {
    final String _sql = "select * from UserVO";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"UserVO"}, false, new Callable<List<UserVO>>() {
      @Override
      public List<UserVO> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final List<UserVO> _result = new ArrayList<UserVO>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UserVO _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new UserVO(_tmpTitle);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
