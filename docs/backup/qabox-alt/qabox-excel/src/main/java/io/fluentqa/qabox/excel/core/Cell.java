/*
 * Copyright 2019 liaochong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fluentqa.qabox.excel.core;

/**
 * excel单元格
 *
 * @author
 * @version 1.0
 */
public class Cell {

    private final int rowNum;

    private final int colNum;

    public Cell(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Cell)) {
            return false;
        } else {
            Cell other = (Cell) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getRowNum() != other.getRowNum()) {
                return false;
            } else {
                return this.getColNum() == other.getColNum();
            }
        }
    }

    private boolean canEqual(Object other) {
        return other instanceof Cell;
    }

    @Override
    public int hashCode() {
        int result = 59 + this.getRowNum();
        result = result * 59 + this.getColNum();
        return result;
    }
}
