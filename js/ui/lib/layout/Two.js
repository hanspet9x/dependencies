import React from 'react'
import { HP } from '../../utils/HP'
import { containerChildrenPadding } from '../../utils/layout_settings'

export default function Two({children, style}) {

    let custom = {padding: containerChildrenPadding}

    return (
        <div className="two">
            <div style={HP.combineStyles({custom, style})}>
                {children}
            </div>
        </div>
    )
}
