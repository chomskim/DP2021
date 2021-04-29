/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimtalk;

/**
 * @author cskim
 *
 */
public class Utils {

	public static final String DEFAULT_DIR = "C:/Temp/";

	public static final int MINPOLYDIST = 6;
	
	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	public static final int EDIT_MOVE = 5;
	
	public static final String[] SHAPE_NAME = {
			"���ٰ���", "Ÿ����", "�ٰ���", "����", "����", "�̵�",
	};
	static public String getExtension(String fileName) {
		String ext = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			ext = fileName.substring(i+1);
		}
		return ext;
	}
	
	
}
