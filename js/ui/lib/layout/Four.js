import React from 'react'
import { HP } from '../../services/HP'
import { containerChildrenPadding } from '../../services/layout_settings'

export default function Four({children, style}) {

    let custom = {padding: containerChildrenPadding}

    return (
        <div className="four">
            <div style={HP.combineStyles(custom, style)}>
                {children}
            </div>
        </div>
    )
}
