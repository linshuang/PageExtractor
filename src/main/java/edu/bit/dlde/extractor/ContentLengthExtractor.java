package edu.bit.dlde.extractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.bit.dlde.extractor.skeleton.ImpreciseExtractor;

/**
 * Efficient Web Page Main Text Extraction towards Online News Analysis
 * 算法：每行内容/最大的行内容
 */
public class ContentLengthExtractor extends ImpreciseExtractor {
	static Logger _logger = Logger.getLogger(ContentLengthExtractor.class);

	private List<String> _src;
	private double[] _n;
	private double _max = 0.0, _min = 0.0, _average = 0.0, _long = 0.0,
			_short = 0.0, _omega1 = 2.0, _omega2 = 2.0;

	private String title = "";
	private String content = "";
	public String total = "";

	/*
	 * the reader should defaultly read in html doc line by line.
	 * 
	 * @see
	 * edu.bit.dlde.extractor.skeleton.PageExtractor#setResource(java.io.Reader,
	 * java.lang.String)
	 */
	public ContentLengthExtractor setResource(Reader reader, String uri) {
		this._reader = reader;
		this._uri = uri;
		initSrc();
		resetAll();
		first();
		normalize();
		smooth();
		setBoundary();
		return this;
	}

//	boolean isEn = true;
	private void initSrc() {
		if (_reader == null)
			return;
		_src = new ArrayList();

		BufferedReader br = new BufferedReader(_reader);
		while (true) {
			try {
				String line = br.readLine();
				if (line == null)
					break;
//				if(line.contains("的"))
//					isEn = false;
				
				_src.add(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void resetAll() {
		_max = 0.0;
		_min = 0.0;
		_average = 0.0;
		_long = 0.0;
		_short = 0.0;
		_omega1 = 2.0;
		_omega2 = 2.0;
	}

	public ContentLengthExtractor() {
	}

	private void first() {
		_n = new double[_src.size()];
		for (int i = 0; i < _n.length; i++) {
			double tmp = _n[i] = _src.get(i).split(" ").length;
//			double tmp = _n[i] = _src.get(i).length();
			// _logger.debug("After the first initial, n[" + i + "] is "
			// + _n[i] + " now.");
			if (tmp > _max) {
				_max = tmp;
			}
		}
	}

	private void normalize() {
		for (int i = 0; i < _n.length; i++) {
			_n[i] /= _max;

			// _logger.debug("After the normalize, n[" + i + "] is " + _n[i]
			// + " now.");
		}
	}

	private void smooth() {
		double tmp = _min = _max = _n[0] = (_omega1 * _n[0] + _n[1])
				/ (_omega1 + 1);
		double sum = tmp;
		int i;

		// _logger.debug("After the smooth, n[" + 0 + "] is " + _n[0]
		// + " now.");

		for (i = 1; i < _n.length - 1; i++) {
			tmp = _n[i] = (_n[i - 1] + _omega1 * _n[i] + _n[i + 1])
					/ (1 + _omega1 + 1);
			if (tmp > _max) {
				_max = tmp;
			}
			if (tmp < _min) {
				_min = tmp;
			}
			sum += tmp;
			// _logger.debug("After the smooth, n[" + i + "] is " + _n[i]
			// + " now.");
		}

		tmp = _n[i] = (_n[i - 1] + _omega1 * _n[i]) / (1 + _omega1);

		// _logger.debug("After the smooth, n[" + i + "] is " + _n[i]
		// + " now.");
		if (tmp > _max) {
			_max = tmp;
		}
		if (tmp < _min) {
			_min = tmp;
		}
		sum += tmp;

		_average = sum / _n.length;
	}

	private void setBoundary() {
		_long = (_omega2 * _max + _average) / (_omega2 + 1);

		// _logger.debug("The long boundary is " + _long + ".");
		_short = (_omega2 * _min + _average) / (_omega2 + 1);

		// _logger.debug("The short boundary is " + _short + ".");
	}

	/**
	 * 通过抽取器活得正文
	 * 
	 * @return List 网页正文集合
	 */
	public Map<String, String> extract() {
		int i, j;
		double tmpWrds = 0, maxWrds = 0;
		boolean existLong = false;

		for (i = 0, j = 0; i < _n.length; i++) {
			// max以上
			if (_n[i] >= _long) {
				tmpWrds += _n[i];
				existLong = true;
			} else {
				// max和min之间
				if (_n[i] >= _short) {
					tmpWrds += _n[i];
				} else {
					// min之下的
					if (existLong && tmpWrds > maxWrds) {
						maxWrds = tmpWrds;
						store(j, i);
						existLong = false;
					}
					j = i + 1;
					tmpWrds = 0;
				}
			}
		}
		return null;
	}

	/**
	 * 将结果序列化到文件
	 * 
	 * @param str
	 *            文件名
	 */
	public void serialize(String str) {
		File f = new File(str);
		BufferedWriter bw = null;

		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(f));
			bw.append(total);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void store(int m, int n) {
		StringBuilder sb = new StringBuilder();
		for (; m < n; m++) {
			sb.append(_src.get(m)).append("\n");
		}
		total = sb.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ContentLengthExtractor cle = new ContentLengthExtractor();
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"/home/lins/data/test/test2")));
		cle.setResource(br, null);
		cle.extract();
		System.out.println(cle.total);
		// cle.serialize("test00");
	}
}
