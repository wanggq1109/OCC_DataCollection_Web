package com.plocc.dc.queue;

public interface Queue<T> {
	/**
	 * push(往queue中添加对象)
	 * 
	 * @param t
	 *            void
	 * @return TODO
	 * @exception
	 * @since 1.0.0
	 */
	public boolean push(T t);

	/**
	 * 得到并移除最先进入的那个对象
	 * 
	 * @return T
	 * @exception
	 * @since 1.0.0
	 */
	public T pop();
}
