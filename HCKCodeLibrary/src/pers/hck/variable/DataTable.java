package pers.hck.variable;

import java.util.LinkedList;
import java.util.List;

public class DataTable {
	public final static int INDEX_ROW = 0;
	public final static int INDEX_COLUMN = 1;

	private final String Warning_InputNull = "Fail! Some input is null!";

	private LinkedList<LinkedList<Object>> DataTable = new LinkedList<LinkedList<Object>>();

	public void addRow(Object[] Columns) {
		if (Columns != null) {
			if (Columns.length == getColumnSize() || getColumnSize() == 0) {
				LinkedList<Object> datas = new LinkedList<Object>();
				for (int i = 0; i < Columns.length; i++) {
					datas.add(Columns[i]);
				}
				DataTable.add(datas);
			} else {
				System.out.println("Wrong array input!");
				for (int i = 0; i < Columns.length; i++) {
					System.out.print(Columns[i] + "|");
				}
				System.out.println();
				System.out.println(getColumnSize());
				System.out.println();
			}
		} else {
			System.out.println(Warning_InputNull);
		}
	}

	public void addColumn(Object[] Rows) {
		if (Rows != null) {
			if (Rows.length == getRowSize() || getRowSize() == 0) {
				if (getRowSize() == 0) {
					setRowSize(Rows.length);
				}
				for (int i = 0; i < Rows.length; i++) {
					LinkedList<Object> datas = getRowDatas(i);
					datas.add(Rows[i]);
					DataTable.set(i, datas);
				}
			} else {
				System.out.println("Wrong array input!");
				for (int i = 0; i < Rows.length; i++) {
					System.out.print(Rows[i] + "|");
				}
				System.out.println();
				System.out.println(getRowSize());
				System.out.println();
			}
		} else {
			System.out.println(Warning_InputNull);
		}
	}

	public boolean removeRow(int RowIndex) {
		if (haveRowIndex(RowIndex)) {
			DataTable.remove(RowIndex);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeColumn(int ColumnIndex) {
		if (haveColumnIndex(ColumnIndex)) {
			for (int i = 0; i < DataTable.size(); i++) {
				LinkedList<Object> datas = DataTable.get(i);
				datas.remove(ColumnIndex);
				DataTable.set(i, datas);
			}
			return true;
		} else {
			return false;
		}
	}

	public Object getData(int RowIndex, int ColumnIndex) {
		return (haveRowIndex(RowIndex) && haveColumnIndex(ColumnIndex)) ? DataTable
				.get(RowIndex).get(ColumnIndex) : null;
	}

	public LinkedList<Object> getRowDatas(int RowIndex) {
		return (haveRowIndex(RowIndex)) ? DataTable.get(RowIndex) : null;
	}

	public LinkedList<Object> getColumnDatas(int ColumnIndex) {
		if (haveColumnIndex(ColumnIndex)) {
			LinkedList<Object> datas = new LinkedList<Object>();
			for (int i = 0; i < getRowSize(); i++) {
				LinkedList<Object> rowDatas = getRowDatas(i);
				datas.add(rowDatas.get(ColumnIndex));
			}
			return datas;
		} else {
			return null;
		}
	}

	public boolean containData(Object Data) {
		if (Data != null) {
			for (int i = 0; i < getColumnSize(); i++) {
				LinkedList<Object> datas = getRowDatas(i);
				if (datas.contains(Data)) {
					return true;
				}
			}
			return false;
		} else {
			System.out.println(Warning_InputNull);
			return false;
		}
	}

	public boolean containData(Object Data, int Index, int ActionIndex) {
		if (Data != null) {
			if (isRow(ActionIndex)) {
				if (haveRowIndex(Index)) {
					LinkedList<Object> rowDatas = getRowDatas(Index);
					return rowDatas.contains(Data);
				}
			} else if (isColumn(ActionIndex)) {
				if (haveColumnIndex(Index)) {
					LinkedList<Object> columnDatas = getColumnDatas(Index);
					return columnDatas.contains(Data);
				}
			}
			return false;
		} else {
			System.out.println(Warning_InputNull);
			return false;
		}
	}

	public LinkedList<Integer[]> searchData(Object Data) {
		if (Data != null) {
			LinkedList<Integer[]> position = new LinkedList<Integer[]>();
			for (int i = 0; i < getRowSize(); i++) {
				LinkedList<Object> datas = getRowDatas(i);
				int index = datas.indexOf(Data);
				if (index >= 0) {
					position.add(new Integer[] { i, index });
				}
			}
			return (position.size() != 0) ? position : null;
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
	}

	public LinkedList<Integer> searchData(Object Data, int Index, int ActionIndex) {
		if (Data != null) {
			LinkedList<Integer> indexs = new LinkedList<Integer>();
			LinkedList<Object> datas = null;
			if (isRow(ActionIndex)) {
				if (haveRowIndex(Index)) {
					datas = getRowDatas(Index);
				}
			} else if (isColumn(ActionIndex)) {
				if (haveColumnIndex(Index)) {
					datas = getColumnDatas(Index);
				}
			}
			if (datas != null) {
				for (int i = 0; i < datas.size(); i++) {
					if (datas.get(i).equals(Data)) {
						indexs.add(i);
					}
				}
			}
			return (indexs.size() != 0) ? indexs : null;
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
	}

	public Object getExchangeData(int InIndex, Object Data, int OutIndex,
			int ActionIndex) {
		if (Data != null) {
			LinkedList<Object> inDatas = null;
			LinkedList<Object> outDatas = null;
			if (isRow(ActionIndex)) {
				if (haveRowIndex(InIndex) && haveRowIndex(OutIndex)) {
					inDatas = getRowDatas(InIndex);
					outDatas = getRowDatas(OutIndex);
				}
			} else if (isColumn(ActionIndex)) {
				if (haveColumnIndex(InIndex) && haveColumnIndex(OutIndex)) {
					inDatas = getColumnDatas(InIndex);
					outDatas = getColumnDatas(OutIndex);
				}
			}
			if (inDatas != null && outDatas != null) {
				int index = inDatas.indexOf(Data);
				if (index >= 0) {
					return outDatas.get(index);
				} else {
					Warning_NoData(Data, 0, InIndex);
					return null;
				}
			}
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
		return null;
	}

	public LinkedList<Object> getExchangeDatas(int InIndex,
			List<Object> Datas, int OutIndex, int ActionIndex) {
		if (Datas != null) {
			LinkedList<Object> datas = new LinkedList<Object>();
			int c = 0;
			for (int i = 0; i < Datas.size(); i++) {
				Object data = getExchangeData(InIndex, Datas.get(i), OutIndex,
						ActionIndex);
				datas.add(data);
				if (data == null) {
					c++;
				}
			}
			return (c < Datas.size()) ? datas : null;
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
	}

	public Object getExchangeData(Object Data, int OutIndex, int ActionIndex) {
		if (Data != null) {
			LinkedList<Object> outDatas = null;
			int index = -1;
			if (isRow(ActionIndex)) {
				if (haveRowIndex(OutIndex)) {
					for (int i = 0; i < getRowSize(); i++) {
						index = getRowDatas(i).indexOf(Data);
						if (index >= 0)
							break;
					}
					outDatas = getRowDatas(OutIndex);
				}
			} else if (isColumn(ActionIndex)) {
				if (haveColumnIndex(OutIndex)) {
					for (int i = 0; i < getColumnSize(); i++) {
						index = getColumnDatas(i).indexOf(Data);
						if (index >= 0)
							break;
					}
					outDatas = getColumnDatas(OutIndex);
				}
			}

			if (index >= 0 && outDatas != null) {
				return outDatas.get(index);
			}

			return null;
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
	}

	public LinkedList<Object> getExchangeDatas(List<Object> Datas,
			int OutIndex, int ActionIndex) {
		if (Datas != null) {
			LinkedList<Object> datas = new LinkedList<Object>();
			int c = 0;
			for (int i = 0; i < Datas.size(); i++) {
				Object data = getExchangeData(Datas.get(i), OutIndex,
						ActionIndex);
				datas.add(data);
				if (data == null) {
					c++;
				}
			}
			return (c < Datas.size()) ? datas : null;
		} else {
			System.out.println(Warning_InputNull);
			return null;
		}
	}

	public int getRowSize() {
		return DataTable.size();
	}

	public int getColumnSize() {
		if (DataTable.size() != 0) {
			return DataTable.get(0).size();
		} else {
			return 0;
		}
	}

	public String toString(){
		if (DataTable.size() !=0){
			String s  ="";
			for(int i=0;i<DataTable.size();i++){
				for(int j=0;j<DataTable.get(i).size();j++){
					s += DataTable.get(i).get(j)+" ||";
				}
				s+="\n";
			}
			return s;
		}else{
			return null;
		}
	}

	private void setRowSize(int size) {
		for (int i = 0; i < size; i++) {
			DataTable.add(new LinkedList<Object>());
		}
	}

	private boolean isRow(int actionIndex) {
		return actionIndex == INDEX_ROW;
	}

	private boolean isColumn(int actionIndex) {
		return actionIndex == INDEX_COLUMN;
	}

	private boolean haveRowIndex(int rowIndex) {
		if (rowIndex < getRowSize() && rowIndex >= 0) {
			return true;
		} else {
			System.out.println("No row index(" + rowIndex + ")!");
			return false;
		}
	}

	private boolean haveColumnIndex(int columnIndex) {
		if (columnIndex < getColumnSize() && columnIndex >= 0) {
			return true;
		} else {
			System.out.println("No column index(" + columnIndex + ")!");
			return false;
		}
	}

	private String getAction(int actionIndex) {
		switch (actionIndex) {
		case 0:
			return "row";
		case 1:
			return "column";
		}
		System.out.println("No this action index(" + actionIndex + ")!");
		return null;
	}

	private String Warning_NoData(Object data, int actionIndex, int index) {
		String title = getAction(actionIndex);
		if (title != null) {
			return "No this data(" + data + ") in " + title + " index(" + index
					+ ")!";
		} else {
			System.out.println("No this title(" + title
					+ ") in method Warning_NoData!");
			return null;
		}
	}
}
