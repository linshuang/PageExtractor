package edu.bit.dlde.extractor.xpathcfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class is used to contain xpath expressions and their names.
 * 
 * @author linss
 * @version 1.0
 * @since 1.6
 */
public class Rule {
	static final List<String> unExpr = Arrays.asList(new String[] { "type",
			"uri" });

	public String _siteType;
	String _uriRegex;
	protected ArrayList<Expression> _exprs = new ArrayList<Expression>();

	public boolean isRuleFit(String uri) {
		return Pattern.matches(_uriRegex, uri);
	}

	public void addExpr(String name, String expr) {
		_exprs.add(new Expression(name, expr));
	}

	public String getExprValue(int i) {
		return _exprs.get(i).expression;
	}

	public String getExprName(int i) {
		return _exprs.get(i).name;
	}

	public int getExprsSize() {
		return _exprs.size();
	}

	public void addUnExpr(String... str) {
		if (str.length < 2)
			return;
		if ("type".equals(str[0])) {
			_siteType = str[1];
		} else if ("uri".equals(str[0])) {
			_uriRegex = str[1];
		}
	}

	class Expression {
		protected String expression;
		protected String name;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            name of the xpath expression. It should be the same as the
		 *            table in DB and Table.cname.
		 * @param expression
		 *            string of xpath expression.
		 */
		public Expression(String name, String expression) {
			this.expression = expression;
			this.name = name;
		}

		public Expression() {
		}

		/**
		 * Get the name of xpath expression.
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Get the xpath expression.
		 * 
		 * @return xpath expression
		 */
		public String getExpression() {
			return expression;
		}

		/**
		 * Set the name
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Set xpath expression
		 * 
		 * @param expression
		 *            String of xpath expression
		 */
		public void setExpression(String expression) {
			this.expression = expression;
		}

	}
}
