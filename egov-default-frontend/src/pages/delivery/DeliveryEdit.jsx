
import React, { useEffect, useState } from 'react'

import { Link, useLocation, useNavigate } from 'react-router-dom'

import * as EgovNet from 'api/egovFetch'
import { NOTICE_BBS_ID } from 'config'
import CODE from 'constants/code'
import URL from 'constants/url'
import axios from 'axios';

import EgovAttachFile from 'components/EgovAttachFile'
import { default as EgovLeftNav } from 'components/leftmenu/EgovLeftNavInform'
import bbsFormVaildator from 'utils/bbsFormVaildator'

function EgovNoticeEdit(props) {
    console.group("EgovNoticeEdit");
    console.log("EgovNoticeEdit [props] : ", props);
    props = {mode: CODE.MODE_CREATE};

    const navigate = useNavigate();
    const location = useLocation();
    console.log("EgovNoticeEdit [location] : ", location);

    const bbsId = location.state?.bbsId || NOTICE_BBS_ID;
    const nttId = location.state?.nttId || "";

    const [modeInfo, setModeInfo] = useState({ mode: props.mode });
    const [masterBoard, setMasterBoard] = useState({});
    const [boardDetail, setBoardDetail] = useState({ nttSj: '', nttCn: '' });
    const [boardAttachFiles, setBoardAttachFiles] = useState();

    const initMode = () => {
        switch (props.mode) {
            case CODE.MODE_CREATE:
                setModeInfo({
                    ...modeInfo,
                    modeTitle: "등록",
                    method: "POST",
                    editURL: '/deliveries'
                });
                break;
            
			default:
                navigate({pathname: URL.ERROR}, {state: {msg : ""}});
        }
    }

    const updateBoard = () => {
        const formData = new FormData();
        for (let key in boardDetail) {
            formData.append(key, boardDetail[key]);
        }

        const entity = {
            deliveryId: boardDetail.deliveryId,
            orderId: boardDetail.orderId,
            productId: boardDetail.productId,
            productName: boardDetail.productName,
            qty: boardDetail.qty
        };

        axios.post('/deliveries', entity)
        .then(response => {
            const resp = response.data;
            if (resp._links.self.href.split('/').pop()) {
                navigate('/delivery/deliveries');
            } else {
                navigate({pathname: URL.ERROR}, {state: {msg: resp.resultMessage}});
            }
        })
        .catch(error => {
            console.error('Request failed:', error);
            navigate({pathname: URL.ERROR}, {state: {msg: error.message}});
        });
    };

	const Location = React.memo(function Location(masterBoard) {
        return (
            <div className="location">
                <ul>
                    <li><Link to={URL.MAIN} className="home">Home</Link></li>
                    <li><Link to="/delivery/deliveries">Delivery</Link></li>
                    <li>{masterBoard && masterBoard.bbsNm}</li>
                </ul>
            </div>
        )
    });

    useEffect(function () {
        initMode();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    console.groupEnd("EgovNoticeEdit");

    return (
        <div className="container">
            <div className="c_wrap">
                {/* <!-- Location --> */}
                <Location />
                {/* <!--// Location --> */}

                <div className="layout">
                    {/* <!-- Navigation --> */}
                    <EgovLeftNav></EgovLeftNav>
                    {/* <!--// Navigation --> */}

                    <div className="contents NOTICE_LIST" id="contents">
                        {/* <!-- 본문 --> */}

                        <div className="top_tit">
                            <h1 className="tit_1">Delivery</h1>
                        </div>
                        
                        <h2 className="tit_2">{masterBoard && masterBoard.bbsNm} {modeInfo.modeTitle}</h2>

                        <div className="board_view2">
                            <dl>
                                <dt>
                                    <label htmlFor="nttSj">DeliveryId</label>
                                </dt>
                                <dd>
                                    <input className="f_input2 w_full" id="deliveryId" name="deliveryId" type="text"
                                        onChange={e => setBoardDetail({ ...boardDetail, deliveryId: e.target.value })}
                                        maxLength="60" />
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label htmlFor="nttSj">OrderId</label>
                                </dt>
                                <dd>
                                    <input className="f_input2 w_full" id="orderId" name="orderId" type="text"
                                        onChange={e => setBoardDetail({ ...boardDetail, orderId: e.target.value })}
                                        maxLength="60" />
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label htmlFor="nttSj">ProductId</label>
                                </dt>
                                <dd>
                                    <input className="f_input2 w_full" id="productId" name="productId" type="text"
                                        onChange={e => setBoardDetail({ ...boardDetail, productId: e.target.value })}
                                        maxLength="60" />
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label htmlFor="nttSj">ProductName</label>
                                </dt>
                                <dd>
                                    <input className="f_input2 w_full" id="productName" name="productName" type="text"
                                        onChange={e => setBoardDetail({ ...boardDetail, productName: e.target.value })}
                                        maxLength="60" />
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label htmlFor="nttSj">Qty</label>
                                </dt>
                                <dd>
                                    <input className="f_input2 w_full" id="qty" name="qty" type="text"
                                        onChange={e => setBoardDetail({ ...boardDetail, qty: e.target.value })}
                                        maxLength="60" />
                                </dd>
                            </dl>
                            {/* <!-- 버튼영역 --> */}
                            <div className="board_btn_area">
                                <div className="left_col btn1">
                                    <button className="btn btn_skyblue_h46 w_100"
                                        onClick={() => updateBoard()}>저장</button>
                                </div>

                                <div className="right_col btn1">
                                    <Link to="/delivery/deliveries" className="btn btn_blue_h46 w_100">목록</Link>
                                </div>
                            </div>
                            {/* <!--// 버튼영역 --> */}
                        </div>

                        {/* <!--// 본문 --> */}
                    </div>

                </div>
            </div>
        </div>
    );
}

export default EgovNoticeEdit;