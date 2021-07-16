import React from 'react'
import { containerChildrenPadding } from '../../utils/layout_settings'
import { HP } from './../../utils/HP';

export default function Three({children, style}) {

    let custom = {padding: containerChildrenPadding}

    return (
        <div className="three">
            <div style={HP.combineStyles(custom, style)}>
                {children}
            </div>
        </div>
    )
}

