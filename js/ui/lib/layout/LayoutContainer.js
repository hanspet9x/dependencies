import React from 'react'
import { containerChildrenPadding } from './../../utils/layout_settings'
import { HP } from './../../utils/HP';

export default function LayoutContainer({children, style, className}) {

    let custom = {padding: containerChildrenPadding}

    return (
        <div style={HP.combineStyles(custom, style)}>
            <div className={`container ${className}`}>
                {children}
            </div>
        </div>
    )
}
