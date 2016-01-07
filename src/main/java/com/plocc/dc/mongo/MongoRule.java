package com.plocc.dc.mongo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 过滤MONGODB非法字符
 * 
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class MongoRule {
	private static final String[] disabledDbName = { "local", "system",
			"admin", "config" };
	private static final String[] disabledCollectionName = { "system" };

	private static final String DEFAULT_DBNAME = "logs";
	private static final String DEFAULT_COLLECTIONNAME = "stat";

	private static final String inValidCharPattern = "[^a-zA-Z,0-9]"; // 除了字母数字外全视为非法的字符

	/**
	 * 
	 * The empty string ("") is not a valid database name. A database name
	 * cannot contain any of these characters: ' ' (a single space), ., $, /,\,
	 * or \0 (the null character). Database names should be all lowercase.
	 * Database names are limited to a maximum of 64 bytes.
	 * 
	 * @param dbName
	 * @return
	 */
	public static String dbName(String dbName) {
		dbName = (StringUtils.trimToEmpty(dbName).length() == 0 ? DEFAULT_DBNAME
				: dbName);
		if (ArrayUtils.contains(disabledDbName, dbName)) {
			dbName = DEFAULT_DBNAME;
		}

		// 去掉特殊字符
		dbName = replaceInValidChar(dbName);
		return StringUtils.substring(StringUtils.lowerCase(dbName), 0, 63);
	}

	/**
	 * @param str
	 * @return
	 */
	private static String replaceInValidChar(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		return str.replaceAll(inValidCharPattern, "_");
	}

	/**
	 * The empty string ("") is not a valid collection name. Collection names
	 * may not contain the character \0 (the null character) because this
	 * delineates the end of a collection name. You should not create any
	 * collections that start with system., a prefix reserved for system
	 * collections. For example, the system.users collection contains the
	 * database’s users, and the system.namespaces collection contains
	 * information about all of the database’s collections. User-created
	 * collections should not contain the reserved character $ in the name. The
	 * various drivers available for the database do support using $ in
	 * collection names because some system-generated collections contain it.
	 * You should not use $ in a name unless you are accessing one of these
	 * collections
	 * 
	 * @param collectionName
	 * @return
	 */
	public static String collectionName(String collectionName) {

		collectionName = (StringUtils.trimToEmpty(collectionName).length() == 0 ? DEFAULT_COLLECTIONNAME
				: collectionName);
		if (ArrayUtils.contains(disabledCollectionName, collectionName)) {
			collectionName = DEFAULT_COLLECTIONNAME;
		}

		// 去掉特殊字符
		collectionName = replaceInValidChar(collectionName);
		return StringUtils.substring(StringUtils.lowerCase(collectionName), 0,
				63);
	}

	/**
	 * key 中不包含.和空格
	 * 
	 * @param key
	 * @return
	 */
	public static String key(String key) {
		key = (StringUtils.trimToEmpty(key).length() == 0 ? "key" : key);
		key = StringUtils.trim(key);

		// 去掉特殊字符
		key = StringUtils.replaceEach(key, new String[] { " ", "." },
				new String[] { "_", "_" });
		return StringUtils.substring(key, 0, 63);
	}

	/**
	 * 对值无特殊要求
	 * 
	 * @param value
	 * @return
	 */
	public static String value(String value) {
		return value;
	}

}
