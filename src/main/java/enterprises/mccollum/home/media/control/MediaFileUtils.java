package enterprises.mccollum.home.media.control;

import com.github.amr.mimetypes.MimeTypes;

public final class MediaFileUtils {
	public static String getMimeTypeByFilePath(String filePath){
		return MimeTypes.getInstance()
				.getByExtension(
		filePath.substring(filePath.lastIndexOf(".")+1)
								).getMimeType();
	}
}
