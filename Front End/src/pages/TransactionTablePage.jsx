import React, { useState } from 'react';
import axios from 'axios';
import _ from 'lodash';
const TransactionTablePage = () => {
  const [data, setData] = useState([]);
  const [formData, setFormData] = useState({});
  const [error, setError] = useState({});

  const getData = async (apiUrl, data, header) => {
    //refactor to services folder
    try {
      console.log(data);
      const resp = await axios.put(apiUrl, data, { header });
      setData(resp.data);
    } catch (err) {
      // Handle Error Here
      setError(err.response.data);
      alert(err.response.data);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const { sortCode, accountNumber, startDate, endDate } = formData; //ES6 destructuring
    if (_.isEmpty(sortCode) || _.isEmpty(accountNumber)) {
      //validation or not
      setError({ message: 'Can not be blank' });
      alert('can not be blank');
      return;
    }

    const url = '/api/v1/alltransactions';
    const accountInput = {
      sortCode,
      accountNumber,
      startDate, // Pass startDate value to backend
      endDate, // Pass endDate value to backend
    };
    const headers = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST',
    };
    getData(url, accountInput, headers);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData, //Spread operator
      [name]: value, //object literal notation ES6.
    });
  };
  return (
    <>
      <fieldset>
        <p>
          Description: This is a React+SpringBoot App for Transactions query. As
          a user, who is able to enter Sort Code and Account Number to check
          their transactions. All the relative data for each Transactions should
          be displayed in a table.
        </p>
        There is a demo Rest API payload format:
        <p>
          {' '}
          {JSON.stringify({
            sortCode: '53-68-92',
            accountNumber: '73084635',
          })}
        </p>
        <p>
          <strong>Task1: display current balance for this account</strong>
        </p>
        <p>
          <strong>Task2: try to get transactions during a time range</strong>
        </p>
        <br />
        <br />
        <li>
          Set up two inputs GUI to get a time range.{' '}
          <strong>For example: 2019-04-01 10:30 - 2019-06-01 10:30</strong>{' '}
        </li>
        <li>
          Modify /api/v1/alltransactions REST API with this time range as a new
          parameter{' '}
        </li>
        <li>Render response transactions data in table </li>
      </fieldset>
      <form onSubmit={handleSubmit}>
        <fieldset>
          {/* refactor to core folder */}
          <label>
            <p>Sort Code:</p>
            <input
              type="text"
              name="sortCode"
              onChange={(e) => handleChange(e)}
              value={formData.sortCode || '53-68-92'}
            />
          </label>

          <label>
            <p>Account Number</p>
            <input
              type="text"
              name="accountNumber"
              onChange={(e) => handleChange(e)}
              value={formData.accountNumber || '73084635'}
            />
          </label>

          <label>
            <p>Start Date</p>
            <input
              type="datetime-local" // add datetime-local for perticaluar date with time
              name="startDate"
              onChange={(e) => handleChange(e)}
              value={formData.startDate || ''}
            />
          </label>

          <label>
            <p>End Date</p>
            <input
              type="Datetime-local" // add datetime-local for perticaluar date with time
              name="endDate"
              onChange={(e) => handleChange(e)}
              value={formData.endDate || ''}
            />
          </label>
          <br></br>
          <br></br>
          <button type="submit">Submit</button>
        </fieldset>

        {!_.isEmpty(
          data
        ) /*   I just Simply add Current Balance from data value. 
                So in the data there is alreasy data from account table i just simple take specific data from account table using data.CurrentBalance.
                i checked Account.java file in backend and show that for current balance there is currentBalance keyword is given. */ && (
          <fieldset>
            <p>
              <b>Current Balance: {data.currentBalance}</b>
            </p>
            <table>
              <tbody>
                <tr>
                  <th>Account Number</th>
                  <th>Target Owner Name</th>
                  <th>Amount</th>
                  <th>Date</th>
                </tr>
                {data?.transactions.length ? (
                  data?.transactions?.map((transaction) => {
                    return (
                      // better to know why needs key here: 1 suppress browser warning, 2 virtual dom
                      <tr key={transaction.id}>
                        <td>{data.accountNumber}</td>
                        <td>{transaction.targetOwnerName}</td>
                        <td>{transaction.amount}</td>
                        <td>{transaction.initiationDate}</td>
                      </tr>
                    );
                  })
                ) : (
                  <tr>
                    <td colSpan="4">No Records Avaliable</td>
                  </tr> // if there is no record it shows no record Avaliable
                )}
              </tbody>
            </table>
          </fieldset>
        )}
      </form>
    </>
  );
};

export default TransactionTablePage;
