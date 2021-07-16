
import React, { useEffect, useMemo, useReducer, useState } from 'react';

import { HiMinusSm, HiOutlinePlusSm } from 'react-icons/hi';
import { MdArrowBack, MdList } from 'react-icons/md';
import LoadingBar from './LoaderBar';
import { HP } from './../utils/HP';
import { useRef } from 'react';
import { FaArrowRight } from 'react-icons/fa';
import {BsListTask} from 'react-icons/bs';
import CheckBox from './CheckBox';
// import { useCallback } from 'react';

export const T_String = "i";
export const T_Number = "n";
export const T_Select = "s";
export const T_TextArea = "t";
export const T_Check = "c";
export const T_File = "f";
export const T_Date = "d";
export const T_Time = "tt";
export const T_DataList = "dd";
export const T_String_ReadOnly = "i_ro";
export const T_Number_ReadOnly = "n_ro";
export const T_Select_ReadOnly = "s_ro";
export const T_TextArea_ReadOnly = "t_ro";
export const T_Check_ReadOnly = "c_ro";
export const T_Date_ReadOnly = "d_ro";
export const T_Time_ReadOnly = "tt_ro";
export const T_String_Required = "i_r";
export const T_Number_Required = "n_r";
export const T_Select_Required = "s_r";
export const T_TextArea_Required = "t_r";
export const T_File_Required = "f_r";
export const T_Date_Required = "d_r";
export const T_Time_Required = "tt_r";
export const T_DataList_Required = "dd_r";

const Filter_Values = ["Empty-Column", "Reset-Filter", "$$filter-by$$", "$$reset-col$$"];

let sortedColumnName = false;

export const removeUndefined = (data = []) => {
    let changed = [...data];
    return changed.filter(e => e !== undefined);
}

export const getUpdatedRows = (storeUpdatedData, changedData, index) => {
    let s = [...storeUpdatedData];
    s[index] = changedData;
    return s.filter(e => e !== undefined);
}

export const getRows = (storeUpdatedData, changedData, index) => {
    let s = [...storeUpdatedData];
    s[index] = changedData;
    return s;
}

const reduxers = (state, action) => {
    return { ...state, ...action };
}

function Table({ data = [], loading = false, editable = false, columnTitles = [],
    excludeColumns = [], mapColumnTitles = {}, camelToGapTitle = false,
    snakeToGapTitles = false, columnsDataTypes = {}, title = "", onAddedRows,
    autoEditTitles = false, showSN = true, rowsPerPage = -1,
    EditView, onDataChange, onRowChange, noData = true, noDataView = undefined,
    onNewRowChanged, newRowChanged, onRowClicked, onRowDoubleClicked, readOnlyColumns = [],
    onRowChecked, showAdd = true, showCheckBox = false
}) {
    let sn = 'sn';

    // const [state, dispatch] = useState({ all: [], chunk: [], gotoPage: 0, rowsCount: 0, rowLimit: 0 });

    // const [state, setState] = useState({ gotoPage: 0, });

    const [aux, setAux] = useState({ rowId: 0, show: false, location: { x: 0, y: 0 } });

    const [addRow, setAddRow] = useState({ show: false, rowCount: 1 });

    const [state, dispatch] = useReducer(reduxers, { all: [], chunk: [], tempAll: [], tempChunk: [], tempData: [], gotoPage: 0, rowsCount: 0, rowLimit: 0, chunked: false });

    const [view, setView] = useState({ showView: false });

    const [filter, setFilter] = useState({ filterBy: "", filterValue: "" });

    const [showCheck, setShowCheck] = useState(false);

    const [checkedList, setCheckedList] = useState([]);

    const [allChecked, setAllChecked] = useState(false);

    /**
     * returns the lowercase of a row as the key and the row key as the value.
     * {lastName: "Akin"} to {lastname: lastName}
     */
    const trackedColumns = useMemo(() => {

        if (data.length > 0) {
            let memo = {};
            Object.entries(data[0]).forEach((e) => {
                memo = { ...memo, [e[0].toLowerCase()]: e[0] };
            });
            return memo;
        }
    }, [data]);

    const excludedData = useMemo(() => {

        if (excludeColumns.length > 0 && data.length > 0) {

            return HP.filterObject(data, excludeColumns);
        }
        return data;

    }, [data]);

    const onPrevious = () => {

        if (state.rowsCount < rowsPerPage) {
            let min = 0;
            let newChunk = state.all.slice(min, rowsPerPage);
            dispatch({ chunk: newChunk, rowsCount: min });
        } else {
            let min = state.rowsCount - rowsPerPage;
            let newChunk = state.all.slice(min, state.rowsCount);
            if (min < data.length) dispatch({ ...state, chunk: newChunk, rowsCount: min });
        }

    }

    const onNext = () => {

        let min = Math.min(data.length, state.rowsCount + rowsPerPage);

        if (state.rowsCount < data.length) {

            let newChunk = state.all.slice(state.rowsCount, min);

            dispatch({ chunk: newChunk, rowsCount: min });
        }
    }

    const onFirst = () => {
        let newChunk = state.all.slice(0, rowsPerPage);

        dispatch({ chunk: newChunk, rowsCount: newChunk.length });
    }

    const onLast = () => {
        let newChunk = state.all.slice(data.length - rowsPerPage, data.length);

        dispatch({ chunk: newChunk, rowsCount: data.length - rowsPerPage });
    }

    const onGotoPage = (n) => {

        if (n <= 0) return;

        let maxRowCount = n * rowsPerPage;

        if (maxRowCount > data.length) maxRowCount = data.length;

        let newChunk = state.all.slice(maxRowCount - rowsPerPage, maxRowCount);

        dispatch({ chunk: newChunk, rowsCount: maxRowCount });
    }

    useEffect(() => {

        let renderData = excludedData;

        if (showSN) {

            renderData = excludedData.map((e, i) => {
                return { [sn]: i + 1, ...e }
            });
        }

        if (rowsPerPage > 0) {

            let chunk = renderData.slice(0, rowsPerPage);

            dispatch({ all: renderData, chunk: chunk, rowsCount: chunk.length, chunked: true });

        } else {

            dispatch({ all: renderData, chunked: false });
        }


        // console.log(state.chunk);
    }, [rowsPerPage, showSN, sn, excludedData]);


    const handleKeyGotoChanged = (e) => {
        if (e.keyCode === 13) {
            onGotoPage(state.gotoPage);
        }
    }

    const handleGotoChange = ({ target }) => {
        dispatch({ gotoPage: target.value });
    }

    const onRowClick = ({ target }) => {
        if (onRowClicked !== undefined && typeof onRowClicked === "function") {
            onRowClicked(target, parseInt(target.id));
        }
    }

    const onRowDoubleClick = ({target}) => {
        if (onRowDoubleClicked !== undefined && typeof onRowDoubleClicked === "function") {
            onRowDoubleClicked(target, parseInt(target.id));
        }
    }

    const onRowRightClick = (e) => {
        document.oncontextmenu = e => e.preventDefault();
        const { target } = e;
        setAux({ rowId: parseInt(target.id), show: false, location: { x: e.clientX, y: e.clientY } });
        document.oncontextmenu = null;
    }

    const onColumnClick = ({ target }) => {

        let colName = trackedColumns[onChangeTitlesByProps(target.textContent, true).toLowerCase()];

        if (colName === undefined) colName = sn;


        HP.sortObjectArray(getTableActiveRows(), colName, sortedColumnName ? false : true);

        sortedColumnName = !sortedColumnName;

        dispatch((state) => ({ [getTableActiveKey()]: getTableActiveRows().map(e => e) }));

    }

    const onCellDoubleClick = (e) => {

    }

    const onCellChanged = ({ target }) => {

        const { name, value, dataset } = target;

        let updatedData = false;

        let indexInData = parseInt(name);

        let newData = [];
        let newState;

        if (state.chunked) {

            let chunkState = [...state.chunk];

            chunkState[indexInData] = { ...chunkState[indexInData], [dataset.key]: value }

            //next page
            if (state.rowsCount > rowsPerPage) {
                indexInData = (state.rowsCount - state.chunk.length) + indexInData;

            }

            newState = [...state.all];

            newData = [...data];

            newState[indexInData] = { ...newState[indexInData], [dataset.key]: value }

            if (newData[indexInData].hasOwnProperty(dataset.key)) {
                updatedData = true;
                newData[indexInData] = { ...newData[indexInData], [dataset.key]: value }
            }

            dispatch({ all: newState, chunk: chunkState });

        } else {

            newState = [...state.all];

            newData = [...data];

            newState[indexInData] = { ...newState[indexInData], [dataset.key]: value }

            if (newData[indexInData].hasOwnProperty(dataset.key)) {

                updatedData = true;

                newData[indexInData] = { ...newData[indexInData], [dataset.key]: value }
            }

            dispatch({ all: newState });

        }

        if (updatedData) {

            if (onDataChange) onDataChange(getCleanChangedDataMany(newData, newState));

            if (onRowChange) onRowChange(getCleanChangedData(newData[indexInData], newState[indexInData]), indexInData);
        }


    }

    /**
     * It removes the key not in data prop.
     * @param {Object} dirtyData Data
     */
    const getCleanChangedData = (mainData, updatedData) => {
        let mData = { ...mainData, ...updatedData };
        if (excludeColumns.length > 0) {
            const fData = data[0];
            for (const key in mData) {
                if (!fData.hasOwnProperty(key)) {
                    delete mData[key];
                }
            }
        }
        return mData;
    }

    const getCleanChangedDataMany = (mainData = [], allData = []) => {
        return mainData.map((data, i) => {
            return getCleanChangedData(data, allData[i]);
        });
    }

    const onFilter = (columnName, columnValue) => {
   
        let mColumnValue = columnValue === Filter_Values[0] ? "" : columnValue;

        if (columnValue === Filter_Values[1]) {
            //reset
            if (state.tempAll.length > 0) {
                let all = [...state.tempAll];
                let chunk = [...state.tempChunk];

                dispatch({ ...state, tempAll: [], tempChunk: [], all: all, chunk: chunk });
            }

        } else {
            let data = getTableActiveRows();

            const filtered = data.filter(e => e[columnName]+"" == mColumnValue+"");
         
            if (filtered.length > 0) {
                let min = Math.min(rowsPerPage, filtered.length)
                let slice = filtered.slice(0, min);

                if (state.tempAll.length === 0) {
                    let temp = [...state.all];
                    let tempChunk = [...state.chunk];
                    dispatch({ ...state, tempAll: temp, tempChunk: tempChunk, all: filtered, chunk: slice });
                } else {

                    dispatch({ ...state, all: filtered, chunk: slice });
                }
            }
        }

    }

    const onFilterChange = ({ target }) => {

        const { name, value } = target;
        setFilter({ ...filter, [name]: value });

    }

    const onFilterEffect = () => {
        if (filter.filterBy !== Filter_Values[2] && filter.filterBy !== "") {
            onFilter(getRealDataColumnName(filter.filterBy), filter.filterValue);
        }

    }

    const getRealDataColumnName = (name) => {
        if (columnTitles.length > 0 && columnTitles.includes(name)) {
            let index = columnTitles.indexOf(name);
            return Object.keys(data[0])[index];
        }

        let mapArray = Object.values(mapColumnTitles);

        if (mapArray.length > 0 && mapArray.includes(name)) {
            let index = columnTitles.indexOf(name);
            return Object.keys(mapColumnTitles)[index];
        }

        return onChangeTitlesByProps(name, true);
    }

    const onViewEdit = () => {

    }



    const onGetColumnTitle = () => {

        if (!state.all.length) return [];

        if (columnTitles.length) {

            if (typeof columnTitles[0] === "string") {

                return columnTitles.map((title, i) => getTH(title, i));

            } else {

                console.error("columnTitles prop should be an array of string.");
            }
        } else {
            return Object.entries(state.all[0]).map(([title], i) => {

                if (typeof mapColumnTitles === "object" && Object.keys(mapColumnTitles).length) {
                    if (mapColumnTitles.hasOwnProperty(title)) {
                        return getTH(mapColumnTitles[title], i);
                    }
                    return getTH(onChangeTitlesByProps(title), i);

                    //autoEditable takes precedence after mapColumnTitles
                } else {
                    return getTH(onChangeTitlesByProps(title), i);
                }

            });

        }


    }


    /**
     * It fires on checkbox checked state change. returns {e: {target: {name: checkbox.name, value: checkbox.value, checked: checkbox.checked}}}
     * @param {Object} param0 Checkbox OnChange argument
     */
    const onCheckChange = ({ target }) => {
        const { value, checked } = target;
        let all;
        let id = parseInt(value);
        if (checked) {
            setCheckedList(checkedList => {
                checkedList[id] = getTableActiveRows()[id];
                all = checkedList;
                return checkedList;
            });
        } else {
            if (checkedList[id] !== undefined) {
                setCheckedList(checkedList => {
                    checkedList[id] = undefined;
                    all = checkedList;
                    return checkedList;
                });
            }
        }
        if (onRowChecked !== undefined) {
            onRowChecked(removeUndefined(all));
        }

    }

    const onGetRows = () => {
        let data = getTableActiveRows();
        return data.map((row, i) => {

            let rowData = Object.entries(row).map((rowData, ii) => getRow(data, rowData, i, ii));
            //add elements to row
            return <tr onClick={onRowClick} onDoubleClick={onRowDoubleClick} style={styles.tr} id={i} onAuxClick={onRowRightClick} key={i}>

                <td style={HP.combineStyles(styles.checkTD, showCheck ? styles.checkShow : styles.checkHide)}>

                    <CheckBox checked={allChecked} value={i} onChange={onCheckChange} />

                </td>

                {rowData}
            </tr>
        });
    }

    const getRow = (source, [key, data], i, ii) => {


        const getTD = (children) => {

            return <td key={ii} style={styles.td} onDoubleClick={onCellDoubleClick}>
                {children}
            </td>


        }

        let block = readOnlyColumns.includes(key);

        if (typeof data === "string") {

            if (data.startsWith("data:image/")) {

                return getTD(<img src={data} style={styles.image} alt="hjk" />);

            } else {
                let len = source[i][key].length;
                if (len > 20) return getTD(<textarea readOnly={editable ? block ? true : false : true} style={styles.textArea} onChange={onCellChanged} data-index={2} data-key={key} type="text" name={i} value={source[i][key]} />);

                return getTD(<input readOnly={editable ? block ? true : false : true} style={styles.input} onChange={onCellChanged} data-index={2} data-key={key} type="text" name={i} value={source[i][key]} />);
            }
        } else if (typeof data === "number") {

            return getTD(<input readOnly={editable ? block ? true : false : true} style={styles.input} type="number" data-index={2} data-key={key} name={i} onChange={onCellChanged} value={source[i][key]} />);

        } else {

            return getTD(<input readOnly={editable ? block ? true : false : true} style={styles.input} type="text" data-index={2} data-key={key} name={i} onChange={onCellChanged} value={source[i][key]} />);

        }

    }

    const getTH = (value, key) => {
        return key >= 0 ? <th key={key} onClick={onColumnClick} style={styles.heading}>{value}</th> : <th >{value}</th>;
    }

    const onChangeTitlesByProps = (title, reverse = false) => {

        if (autoEditTitles) {
            return reverse ? HP.gapToCamel(HP.gapToSnake(title)) : HP.camelToGap(HP.snakeToGap(title), true);
        } else {
            //check snake or camel
            let edited = ""
            if (snakeToGapTitles) {
                edited = reverse ? trackedColumns[HP.gapToSnake(title).toLowerCase()] : HP.snakeToGap(title, true);

            }
            if (camelToGapTitle) {
                edited = reverse ? trackedColumns[HP.gapToCamel(title).toLowerCase()]  : HP.camelToGap(title, true);
               
            }

            return edited ? edited : title;
        }
    }

    const getTableActiveRows = () => {
        return state.chunked ? state.chunk : state.all;
    }

    const getTableActiveKey = () => {
        return state.chunked ? "chunk" : "all";
    }

    const getFilterColumns = useMemo(() => {

        if (!state.all.length) return [];

        if (columnTitles.length) {

            if (typeof columnTitles[0] === "string") {

                return columnTitles.map((title) => title);

            } else {

                console.error("columnTitles prop should be an array of string.");
            }
        } else {
            return Object.entries(state.all[0]).map(([title], i) => {

                if (typeof mapColumnTitles === "object" && Object.keys(mapColumnTitles).length) {
                    if (mapColumnTitles.hasOwnProperty(title)) {
                        return mapColumnTitles[title];
                    }
                    return onChangeTitlesByProps(title);

                    //autoEditable takes precedence after mapColumnTitles
                } else {
                    return onChangeTitlesByProps(title);
                }

            });

        }
    }, [state.all.length, columnTitles.length]);

    const onCheckControlChange = ({ target }) => {
  
        setAllChecked(target.checked);
    }

    const getColumns = useMemo(() => {
      
        let headColumns;
        if (!state.all.length) return [];

        if (columnTitles.length) {

            if (typeof columnTitles[0] === "string") {

                headColumns = columnTitles.map((title, i) => getTH(title, i));


            } else {

                console.error("columnTitles prop should be an array of string.");
            }
        } else {
            headColumns = Object.entries(state.all[0]).map(([title], i) => {

                if (typeof mapColumnTitles === "object" && Object.keys(mapColumnTitles).length) {
                    if (mapColumnTitles.hasOwnProperty(title)) {
                        return getTH(mapColumnTitles[title], i);
                    }
                    return getTH(onChangeTitlesByProps(title), i);

                    //autoEditable takes precedence after mapColumnTitles
                } else {
                    return getTH(onChangeTitlesByProps(title), i);
                }

            });

        }
        headColumns.unshift(<th style={HP.combineStyles(styles.checkTD, showCheck ? styles.checkShow : styles.checkHide)} key={headColumns.length}>
            <CheckBox color="red" onChange={onCheckControlChange} />
        </th>)
        return headColumns;
    }, [state.all.length, columnTitles.length, showCheck]);


    const onAuxListClicked = (rowId, type) => {
        switch (type) {
            case 1: //edit

                break;

            default:
                break;
        }
    }

    const handleNewRow = (newRows) => {
        if (onAddedRows) onAddedRows(newRows);
    }

    const handleAddRowClick = () => {
        if (addRow.show) {
            //increase row count
            setAddRow((addRow) => {
                return { ...addRow, rowCount: addRow.rowCount + 1 }
            });
        } else {
            if (EditView !== undefined) {
                setView((showView) => !showView);
            } else {
                setAddRow({ ...addRow, show: true });
            }

        }
    }

    const onReduceRowCount = () => {
        if (addRow.rowCount > 0) {
            setAddRow((addRow) => {
                return { ...addRow, rowCount: addRow.rowCount - 1 }
            });
        }
    }

    const onCloseAddRow = () => {
        if (view.showView) {
            setView((showView) => !showView);
        } else {
            setAddRow({ ...addRow, show: false });
        }
    }

    const handleNewRowchange = (row, name, value, rowIndex) => {

        if (typeof onNewRowChanged === "function") {
            onNewRowChanged(row, name, value, rowIndex);
        }
    }

    if (data.length <= 0 && noData === true) {
        if (noDataView !== undefined) {
            return noDataView;
        }
        return <div style={{ width: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <h1>No Data</h1>
        </div>
    }

    return (
        <div style={styles.wrapper}>

            <AuxView viewProp={false} rowId={aux.rowId} location={aux.location} show={aux.show} onAuxClick={onAuxListClicked} />

            <div style={styles.tableTop}>

                <div>
                    <h3 style={{ color: '#444444', padding: 0, margin: 0 }}>{title}</h3>
                    <div style={styles.filterWrapper}>

                        <select style={styles.filterBy} onChange={onFilterChange} name="filterBy" value={state.filterBy}>
                            <option value={Filter_Values[2]}>Filter By:</option>

                            {getFilterColumns.map((e, i) => <option key={i}>{e}</option>)}
                        </select>

                        <input style={styles.filterName} list="filter" placeholder="search.." name="filterValue" onChange={onFilterChange} value={filter.filterValue} />
                        <datalist id="filter">
                            <option value={Filter_Values[0]} />
                            <option value={Filter_Values[1]} />
                        </datalist>
                        <FaArrowRight style={styles.filterGo} onClick={onFilterEffect} />

                    </div>

                </div>
                {/* Add New Row Control Buttons */}
                <div style={{ display: 'flex', alignItems: 'center' }}>

                    { addRow.show === false ? showCheckBox ? <MdList style={styles.tableTopAddIcon} onClick={() => setShowCheck((showCheck) => !showCheck)} /> : "" : "" }

                    {addRow.show || view.showView ? <MdArrowBack style={styles.tableTopAddIcon} onClick={onCloseAddRow} title="Show table data." /> : ""}

                    {/* {addRow.show ? <IoCheckmarkOutline style={styles.tableTopAddIcon} onClick={onReduceRowCount} title="Save Entries" /> : ""} */}

                    {showAdd ? <HiOutlinePlusSm style={styles.tableTopAddIcon} onClick={handleAddRowClick} title="Add row." /> : ""}

                    {addRow.show ? <HiMinusSm style={styles.tableTopAddIcon} onClick={onReduceRowCount} title="Delete last row." /> : ""}

                </div>
            </div>
            <LoadingBar show={loading} />
            {/* Toggle edit view and table */}
            <div style={styles.tableWrapper}>
                {view.showView ? EditView :
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                {getColumns}
                            </tr>
                        </thead>
                        <tbody>
                            {/* Toggle new and old list */}
                            {addRow.show ?
                                <AddRow data={excludedData} loading={loading} onRowChange={handleNewRowchange} rowChanged={newRowChanged}
                                    dataTypes={columnsDataTypes} onSubmit={handleNewRow} rowCount={addRow.rowCount} /> :
                                onGetRows()}
                        </tbody>
                    </table>
                }
            </div>
            {/* Render next buttons if rowsperpage is positive and data length - rowsperpage has other page */}
            {rowsPerPage !== -1 && data.length - rowsPerPage && !addRow.show ?
                <div style={styles.bottom}>
                    <div>
                        <button onClick={onFirst} style={styles.button}>first</button>
                        <button onClick={onNext} style={styles.button}>next</button>
                        <button onClick={onPrevious} style={styles.button}>previous</button>
                        <button onClick={onLast} style={styles.button}>last</button>
                    </div>
                    <div>
                        <input type="number" style={styles.pageNo} name="gotoPage" value={state.gotoPage} onChange={handleGotoChange}
                            onKeyUp={handleKeyGotoChanged} />
                        <button onClick={onGotoPage.bind(null, state.gotoPage)} style={styles.button}>GO</button>
                    </div>
                </div> : ""
            }
        </div>
    );
}

const AddRow = ({ dataTypes, onSubmit, data = [], rowCount = 1, onRowChange, rowChanged = {}, loading }) => {
    // console.log(dataTypes);
    const dataTypeHasNoName = Array.isArray(dataTypes);

    const indexRef = useRef();

    const fileRef = useRef([]);

    const rowRef = useRef(null);

    let dt = dataTypeHasNoName ? dataTypes.length : Object.keys(dataTypes).length;

    const [state, setState] = useState([]);

    const handleChange = ({ target }) => {

        const { name, value, dataset, type } = target;

        let modValue = value;

        if (type === "checkbox") {
            modValue = target.checked;
        }

        if (type === "file") {
            if (target.files.length > 1) {
                modValue = [value, target.files]
            } else {
                modValue = [value, target.files[0]];
            }
        }

        let idx = parseInt(dataset.id);

        setState((state) => {
            let newState;

            if (state[idx] === undefined) {
                newState = [...state];
                newState[idx] = { [name]: modValue };
            } else {

                newState = [...state];
                //setrowchanged

                if (rowRef.current !== null) {
                    console.log(rowRef.current);
                    newState[indexRef.current] = { ...state[indexRef.current], ...rowRef.current };
                    if (indexRef.current === idx) {
                        newState[idx] = { ...newState[idx], [name]: modValue };
                    } else {
                        newState[idx] = { ...state[idx], [name]: modValue };
                    }
                    rowRef.current = null;
                } else {
                    newState[idx] = { ...state[idx], [name]: modValue };
                }


            }
            onRowChange(newState[idx], name, modValue, idx);
            indexRef.current = idx;
            // console.log(newState);
            return newState;

        });


    }

    const handleBlur = ({ target }) => {
        // const { name, value, dataset, type } = target;
    }

    useEffect(() => {
        if (Object.keys(rowChanged).length) {

            rowRef.current = rowChanged;
        }
    });

    const onComplete = () => {

        // let mState = [...state, []];

        // fileRef.current.forEach( (e, i) => {
        //     if(e !== undefined){
        //         const {name, value} = e;    
        //         mState[i][name] = value;
        //     }
        // });

        onSubmit(state);
    }

    if (data.length === 0 || rowCount === 0) {
        return "";
    }

    if (Object.keys(dataTypes).length) {

    }

    const getInputTypeFromArray = (i, nameOrIndex, name, modValue) => {
        // console.log(i, nameOrIndex, name, value);
        if (nameOrIndex !== undefined && typeof nameOrIndex === "object") {

            if (nameOrIndex.constructor === Array) {

                if (Array.isArray(nameOrIndex)) {
                    return getSelect(nameOrIndex, i, name, modValue);

                }
            } else {// it is an object with type and value
                const { type, value } = nameOrIndex;

                if (typeof value === "object") {

                    switch (type) {
                        case T_DataList:
                            return getDataList(value, i, name, modValue);
                        case T_Select:
                            return getSelect(value, i, name, modValue);
                        default:
                    }
                } else {
                    return getInputsObj(type, i, name, modValue ? modValue : value);
                }

            }
        } else {

            return getInputsObj(nameOrIndex, i, name, modValue);
        }
    }

    const getSelect = (map, dataId, name, value) => {
        return (
            <select style={styles.input} data-id={dataId} onChange={handleChange} name={name} value={value}>
                <option>choose</option>
                {map.map((e, i) => {
                    if (Array.isArray(e)) {
                        return <option key={i} value={e[0]}>{e[1]}</option>
                    } else {
                        return <option key={i} value={e}>{e}</option>
                    }
                })}
            </select>
        )
    }

    const getDataList = (map, dataId, name, value) => {
        return (
            <>
                <input style={styles.input} list={`datalist${dataId}`} data-id={dataId} onChange={handleChange} name={name} value={value} />
                <datalist id={`datalist${dataId}`}>
                    {map.map((e, i) => {
                        if (Array.isArray(e)) {
                            return <option key={i} value={e[0]}>{e[1]}</option>
                        } else {
                            return <option key={i} value={e}>{e}</option>
                        }
                    })}
                </datalist>
            </>
        );
    }

    const getInputsObj = (type, i, name, modValue) => {


        switch (type) {

            case T_String:
                return <input type="text" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_String_ReadOnly:

                return <input type="text" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} readOnly />

            case T_String_Required:
                return <input type="text" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} required />

            case T_Number:
                return <input type="number" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Number_ReadOnly:
                return <input readOnly type="number" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Number_Required:
                return <input type="number" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} required />

            case T_Date:
                return <input type="date" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Date_ReadOnly:
                return <input readOnly type="date" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Date_Required:
                return <input type="date" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} required />

            case T_Time:
                return <input type="time" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Time_ReadOnly:
                return <input readOnly type="time" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Time_Required:
                return <input type="time" data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_Check:
                return <input type="checkbox" data-id={i} onChange={handleChange} name={name} value={modValue} />

            case T_Check_ReadOnly:
                return <input type="checkbox" data-id={i} onChange={handleChange} name={name} value={modValue} readOnly />

            case T_File:
                console.log(i, name, modValue)
                fileRef.current[i] = { name: name, value: Array.isArray(modValue) ? modValue[1] : "" };
                return <input type="file" data-id={i} onChange={handleChange} name={name} multiple value={Array.isArray(modValue) ? modValue[0] : modValue} />

            case T_File_Required:
                fileRef.current[i] = { name: name, value: Array.isArray(modValue) ? modValue[1] : "" };
                return <input type="file" data-id={i} onChange={handleChange} name={name} multiple value={Array.isArray(modValue) ? modValue[0] : modValue} required />

            case T_TextArea:
                return <textarea data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_TextArea_ReadOnly:
                return <textarea readOnly data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} />

            case T_TextArea_Required:
                return <textarea data-id={i} onChange={handleChange} name={name} value={modValue} style={styles.input} required />

            default:
                return <input data-id={i} type="text" onChange={handleChange} name={name} value={modValue} style={styles.input} />
        }

    }

    const getInputByDataType = (rowIndex, dataIndex, name, value) => {

        if (dataTypeHasNoName) {

            return getInputTypeFromArray(rowIndex, dataTypes[dataIndex], name, value);

        } else if (dataTypes) {
            //data type is object

            return getInputTypeFromArray(rowIndex, dataTypes[name], name, value);

        }

    }

    return <React.Fragment>
        {HP.getArrayOfNum(rowCount).map((rowNo) => {

            return (
                <tr key={rowNo} style={styles.tr}>
                    {/* SN */}
                    <td style={HP.combineStyles(styles.td, { paddingLeft: 8 })}>{rowNo + 1}</td>

                    {Object.entries(data[0]).map(
                        // data[0] = {key: "", key2: ""}
                        ([column], dataIndex) => {
                            // [key2], 1 
                            return <td key={dataIndex} style={styles.td}>

                                {
                                    dt > 0 ?
                                        getInputByDataType(rowNo, dataIndex, column, state[rowNo] ?
                                            state[rowNo][column] : "") :

                                        <input style={styles.input} data-id={rowNo} onChange={handleChange} onBlur={handleBlur} type="text" name={column} value={state[rowNo] ? state[rowNo][column] : ""} />

                                }


                            </td>
                        })}
                </tr>
            )
        })}

        <tr style={{ backgroundColor: 'unset' }}>
            <td style={{ paddingTop: 20 }}>
                <button onClick={onComplete} disabled={loading}>Done</button>
            </td>
        </tr>
    </React.Fragment>

}

const AuxView = ({ rowId, onAuxClick, show = false, viewProp = false, location = { x: 0, y: 0 } }) => {
    return (
        <ul style={styles.auxView(show, location.x, location.y)}>
            {viewProp ? <li onClick={onAuxClick.bind(null, rowId, 0)}>View</li> : ""}
            <li onClick={onAuxClick.bind(null, rowId, 1)}>Edit</li>
            <li onClick={onAuxClick.bind(null, rowId, 2)}>Delete</li>
        </ul>
    );
}

const styles = {
    wrapper: {
        // overflow: 'auto',
        width: '100%'
    },
    tableWrapper: {
        overflow: 'auto',
        width: '100%',
        scrollbar: 6,
        scrollbarTrack: '#922'

    },
    table: {
        width: '100%',

    },
    tr: {
        borderBottom: 'solid 1px black'
    },
    td: {
        backgroundColor: 'white',
        padding: 0,
    },
    checkTD: {
        position: 'absolute',
        backgroundColor: 'unset',
        padding: 4,
        transition: '.4s ease'
    },

    checkShow: {
        transform: 'translateX(0px)',
        opacity: 1,
    },

    checkHide: {
        transform: 'translateX(-40px)',
        opacity: 0
    },

    filterWrapper: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-start',
    },

    filterBy: {
        outline: 'none',
        border: 'none',
        backgroundColor: '#e1e1e1',
        boxShadow: '0px -1px 2px rgba(0,0,0,0.1) inset',
        height: 30,
        color: '#333',
        padding: 5
    },

    filterName: {
        height: 28,
        border: 'none',
        outline: 'none',
        width: 100,
        paddingRight: 20
    },

    filterGo: {
        color: '#777',
        backgroundColor: '#e1e1e1',
        padding: 2,
        marginLeft: -20,
        cursor: 'pointer',
        boxShadow: '-1px 0px 2px rgba(0,0,0,0.1) inset',
    },



    heading: {
        padding: '4px 30px',
        color: '#444',
        fontWeight: 'lighter',
    },

    image: {
        width: 80
    },

    input: {
        outline: 'none',
        width: 'calc(100% - 16px)',
        border: 'none',
        padding: '10px 8px',
        textAlign: 'center',

    },
    textArea: {
        outline: 'none',
        width: '200px',
        border: 'none',
        padding: '10px 8px',

    },

    tableTop: {
        padding: '2px 10px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#f2f2f2',
        // width: 'calc(100% - 40px)',
        boxSizing: 'border-box',

    },

    tableTopAddIcon: {
        width: 30,
        height: 30,
        color: '#444',
        cursor: 'pointer',
        marginRight: 3
    },

    bottom: {
        marginTop: 5,
        display: 'flex',
        justifyContent: 'space-between',
        // position: 'absolute',
        width: '100%'
    },

    button: {
        padding: '5px 10px',
        marginRight: 2,
        fontSize: 'small',
        color: '#666'
    },
    auxView: (show, x, y) => {
        return {
            margin: 2,
            position: 'fixed',
            opacity: show ? 1 : 0,
            pointerEvents: show ? 'auto' : 'none',
            left: x,
            top: y
        }
    },
    pageNo: {
        height: 28,
        outline: 'none',
        borderRadius: 8,
        marginRight: 4,
        border: 'solid 1px gray'
    },



}
export default Table;