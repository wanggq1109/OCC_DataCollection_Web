package com.plocc.dc.common;

import java.io.File;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.service.MongoService;

public class PostReceiptUtils {

	public static boolean receiptFilter(JSONObject json,MongoService mongoService) {

		try {
			String image = json.getString("image");
			
			if (null == image) {

				return false;

			}
			String path = SystemSetting.getInstance().getImageUrl();
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			String cptId = json.getString("cptid"); // 设备id
			String receiptnum = json.getString("receiptnum"); // 小票单号
			String fileName = cptId + "_" + receiptnum + ".jpg";
			String imageUrl = path + fileName;
			String modelType = json.getString("modelType"); // 云pos模式；1快照模式，2流模式

			if (null == modelType) {

				return false;

			}

			if ("1".equals(modelType)) {

				if (null != image) {

					boolean generateImage = ImageUtils.GenerateImage(image,
							imageUrl);
					if (generateImage) {
						json.put("imageurl", "/" + "receiptimage" + "/"
								+ fileName);

						return true;
					}
				}
			}

			if ("2".equals(modelType)) {
				String textString = image;
				if (textString != null && !textString.equals("")) {
					json.put("textString", textString);
					return true;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
