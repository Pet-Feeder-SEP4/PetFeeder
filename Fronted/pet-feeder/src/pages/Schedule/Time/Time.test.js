import React from "react";
import { render, fireEvent, waitFor, screen } from "@testing-library/react";
import axios from "../../../api/axios"; // Import axios for mocking
import { MemoryRouter, useHistory } from "react-router-dom"; // Import MemoryRouter and useHistory

// Mock react-router-dom
jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"), // Use actual implementation for other exports
  useHistory: jest.fn(),
}));

// Mock axios requests
jest.mock("../../../api/axios");

// Sample test
describe("Time Component", () => {
  // Clean up the mock after the test
  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders component and handles add button click", async () => {
    // Mock useHistory
    const mockHistory = { push: jest.fn() };
    useHistory.mockReturnValue(mockHistory);

    // Mock axios get request
    axios.get.mockResolvedValue({
      data: [
        { timeId: 1, timeLabel: "Mock Time", time: "12:00", portionSize: 2 },
      ],
    });

    // Mock axios post request
    axios.post.mockResolvedValue({ data: "Mock response" });

    // Render the component with MemoryRouter
    render(
      <MemoryRouter>
        <Time />
      </MemoryRouter>
    );

    // Access the input fields
    const timeLabelInput = screen.getByLabelText("Time Label:");
    const timeInput = screen.getByLabelText("Time:");
    const portionSizeInput = screen.getByLabelText("Portion Size:");

    // Fill in input fields
    fireEvent.change(timeLabelInput, { target: { value: "Mock Time Label" } });
    fireEvent.change(timeInput, { target: { value: "12:00" } });
    fireEvent.change(portionSizeInput, { target: { value: "2" } });

    // Click the add button
    fireEvent.click(screen.getByText("Add"));

    // Wait for the axios request to be resolved
    await waitFor(() => expect(axios.post).toHaveBeenCalledTimes(1));

    // Check if useHistory.push was called with the expected path
    expect(mockHistory.push).toHaveBeenCalledWith("/expected-path");

    // Additional assertions can be added based on the component's behavior
  });
});
