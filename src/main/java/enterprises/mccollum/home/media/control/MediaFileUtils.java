package enterprises.mccollum.home.media.control;

import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;

public final class MediaFileUtils {
	public static String getMimeTypeByFilePath(String filePath){
		MimeType mimeType = MimeTypes.getInstance()
				.getByExtension(
		filePath.substring(filePath.lastIndexOf(".")+1)
								);
		if(mimeType == null)
			return "unknown/x";
		return mimeType.getMimeType();
	}
}
